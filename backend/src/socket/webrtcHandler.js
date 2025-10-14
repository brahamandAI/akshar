/**
 * WebRTC Signaling Handler
 * Manages call signaling, ICE candidates, and connection state
 */

const Call = require('../models/Call');
const User = require('../models/User');
const { sendCallNotification } = require('../services/pushNotificationService');

// Store active call sessions
const activeCalls = new Map();
const userSockets = new Map(); // userId -> socketId mapping

/**
 * Initialize WebRTC signaling handlers
 */
function initializeWebRTCHandlers(io, socket, userId) {
    console.log(`[WebRTC] User ${userId} connected`);
    
    // Map user to socket for direct communication
    userSockets.set(userId, socket.id);
    
    // Handle call initiation
    socket.on('call:initiate', async (data) => {
        try {
            const { targetUserId, callType, offer } = data;
            
            console.log(`[WebRTC] Call initiated: ${userId} -> ${targetUserId} (${callType})`);
            
            // Create call record
            const call = await Call.create({
                caller: userId,
                receiver: targetUserId,
                callType,
                status: 'ringing',
                startedAt: new Date()
            });
            
            // Store in active calls
            activeCalls.set(call._id.toString(), {
                callId: call._id,
                caller: userId,
                receiver: targetUserId,
                callType,
                status: 'ringing',
                callerSocket: socket.id
            });
            
            // Get target user's socket
            const targetSocketId = userSockets.get(targetUserId);
            
            if (targetSocketId) {
                // Send call offer to target user
                io.to(targetSocketId).emit('call:incoming', {
                    callId: call._id,
                    caller: userId,
                    callType,
                    offer
                });
                
                // Confirm to caller
                socket.emit('call:initiated', {
                    callId: call._id,
                    status: 'ringing'
                });
            } else {
                // Target user offline, send push notification
                const targetUser = await User.findById(targetUserId);
                if (targetUser && targetUser.fcmToken) {
                    await sendCallNotification(
                        targetUser.fcmToken,
                        userId,
                        callType
                    );
                }
                
                socket.emit('call:user_offline', {
                    callId: call._id,
                    message: 'User is offline'
                });
            }
        } catch (error) {
            console.error('[WebRTC] Call initiation error:', error);
            socket.emit('call:error', { message: error.message });
        }
    });
    
    // Handle call answer
    socket.on('call:answer', async (data) => {
        try {
            const { callId, answer } = data;
            
            console.log(`[WebRTC] Call answered: ${callId}`);
            
            const callSession = activeCalls.get(callId);
            if (!callSession) {
                socket.emit('call:error', { message: 'Call not found' });
                return;
            }
            
            // Update call status
            callSession.status = 'active';
            callSession.receiverSocket = socket.id;
            
            await Call.findByIdAndUpdate(callId, {
                status: 'active',
                answeredAt: new Date()
            });
            
            // Send answer to caller
            io.to(callSession.callerSocket).emit('call:answered', {
                callId,
                answer
            });
            
        } catch (error) {
            console.error('[WebRTC] Call answer error:', error);
            socket.emit('call:error', { message: error.message });
        }
    });
    
    // Handle call rejection
    socket.on('call:reject', async (data) => {
        try {
            const { callId } = data;
            
            console.log(`[WebRTC] Call rejected: ${callId}`);
            
            const callSession = activeCalls.get(callId);
            if (callSession) {
                await Call.findByIdAndUpdate(callId, {
                    status: 'rejected',
                    endedAt: new Date()
                });
                
                // Notify caller
                io.to(callSession.callerSocket).emit('call:rejected', { callId });
                
                // Clean up
                activeCalls.delete(callId);
            }
        } catch (error) {
            console.error('[WebRTC] Call rejection error:', error);
        }
    });
    
    // Handle call end
    socket.on('call:end', async (data) => {
        try {
            const { callId } = data;
            
            console.log(`[WebRTC] Call ended: ${callId}`);
            
            const callSession = activeCalls.get(callId);
            if (callSession) {
                const endTime = new Date();
                const call = await Call.findById(callId);
                
                let duration = 0;
                if (call && call.answeredAt) {
                    duration = Math.floor((endTime - call.answeredAt) / 1000);
                }
                
                await Call.findByIdAndUpdate(callId, {
                    status: 'completed',
                    endedAt: endTime,
                    duration
                });
                
                // Notify other party
                const otherSocket = socket.id === callSession.callerSocket 
                    ? callSession.receiverSocket 
                    : callSession.callerSocket;
                    
                if (otherSocket) {
                    io.to(otherSocket).emit('call:ended', { callId, duration });
                }
                
                // Clean up
                activeCalls.delete(callId);
            }
        } catch (error) {
            console.error('[WebRTC] Call end error:', error);
        }
    });
    
    // Handle ICE candidates exchange
    socket.on('call:ice_candidate', (data) => {
        try {
            const { callId, candidate } = data;
            
            const callSession = activeCalls.get(callId);
            if (callSession) {
                const targetSocket = socket.id === callSession.callerSocket 
                    ? callSession.receiverSocket 
                    : callSession.callerSocket;
                    
                if (targetSocket) {
                    io.to(targetSocket).emit('call:ice_candidate', {
                        callId,
                        candidate
                    });
                }
            }
        } catch (error) {
            console.error('[WebRTC] ICE candidate error:', error);
        }
    });
    
    // Handle call missed (timeout)
    socket.on('call:missed', async (data) => {
        try {
            const { callId } = data;
            
            await Call.findByIdAndUpdate(callId, {
                status: 'missed',
                endedAt: new Date()
            });
            
            activeCalls.delete(callId);
        } catch (error) {
            console.error('[WebRTC] Call missed error:', error);
        }
    });
    
    // Clean up on disconnect
    socket.on('disconnect', () => {
        console.log(`[WebRTC] User ${userId} disconnected`);
        
        userSockets.delete(userId);
        
        // End any active calls for this user
        for (const [callId, callSession] of activeCalls.entries()) {
            if (callSession.caller === userId || callSession.receiver === userId) {
                // Notify other party
                const otherSocket = socket.id === callSession.callerSocket 
                    ? callSession.receiverSocket 
                    : callSession.callerSocket;
                    
                if (otherSocket) {
                    io.to(otherSocket).emit('call:ended', {
                        callId,
                        reason: 'User disconnected'
                    });
                }
                
                // Update call record
                Call.findByIdAndUpdate(callId, {
                    status: 'failed',
                    endedAt: new Date()
                }).catch(err => console.error('Failed to update call:', err));
                
                activeCalls.delete(callId);
            }
        }
    });
}

/**
 * Get active call info
 */
function getActiveCall(callId) {
    return activeCalls.get(callId);
}

/**
 * Get user's socket ID
 */
function getUserSocket(userId) {
    return userSockets.get(userId);
}

module.exports = {
    initializeWebRTCHandlers,
    getActiveCall,
    getUserSocket
};

