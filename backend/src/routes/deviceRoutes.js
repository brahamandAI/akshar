const express = require('express');
const router = express.Router();
const { authMiddleware, asyncHandler, AppError } = require('../middleware');
const LinkedDevice = require('../models/LinkedDevice');
const jwt = require('jsonwebtoken');
const { v4: uuidv4 } = require('uuid');

/**
 * @route   POST /api/devices/generate-qr
 * @desc    Generate QR code token for device linking
 * @access  Private
 */
router.post('/generate-qr', authMiddleware, asyncHandler(async (req, res) => {
    const linkToken = uuidv4();
    const qrData = {
        userId: req.user._id,
        linkToken,
        expiresAt: Date.now() + 5 * 60 * 1000 // 5 minutes
    };
    
    // Store temporarily in user session or cache
    // For simplicity, we'll encode it in JWT
    const qrToken = jwt.sign(qrData, process.env.JWT_SECRET, { expiresIn: '5m' });
    
    res.json({
        success: true,
        qrToken,
        expiresIn: 300 // seconds
    });
}));

/**
 * @route   POST /api/devices/link
 * @desc    Link a new device using QR token
 * @access  Public (uses QR token for auth)
 */
router.post('/link', asyncHandler(async (req, res) => {
    const { qrToken, deviceInfo } = req.body;
    
    if (!qrToken || !deviceInfo) {
        throw new AppError('QR token and device info required', 400);
    }
    
    // Verify QR token
    let decoded;
    try {
        decoded = jwt.verify(qrToken, process.env.JWT_SECRET);
    } catch (error) {
        throw new AppError('Invalid or expired QR token', 401);
    }
    
    const { userId, linkToken } = decoded;
    
    // Create device record
    const deviceId = uuidv4();
    const refreshToken = jwt.sign(
        { deviceId, userId },
        process.env.JWT_SECRET,
        { expiresIn: '30d' }
    );
    
    const linkedDevice = await LinkedDevice.create({
        user: userId,
        deviceId,
        deviceName: deviceInfo.deviceName || 'Unknown Device',
        deviceType: deviceInfo.deviceType || 'web',
        platform: deviceInfo.platform,
        browserInfo: deviceInfo.browserInfo,
        ipAddress: req.ip,
        linkToken,
        refreshToken
    });
    
    // Generate access token for this device
    const accessToken = jwt.sign(
        { id: userId, deviceId },
        process.env.JWT_SECRET,
        { expiresIn: '7d' }
    );
    
    res.json({
        success: true,
        message: 'Device linked successfully',
        device: {
            deviceId: linkedDevice.deviceId,
            deviceName: linkedDevice.deviceName,
            linkedAt: linkedDevice.linkedAt
        },
        token: accessToken,
        refreshToken
    });
}));

/**
 * @route   GET /api/devices
 * @desc    Get user's linked devices
 * @access  Private
 */
router.get('/', authMiddleware, asyncHandler(async (req, res) => {
    const devices = await LinkedDevice.getUserDevices(req.user._id);
    
    res.json({
        success: true,
        devices: devices.map(d => ({
            deviceId: d.deviceId,
            deviceName: d.deviceName,
            deviceType: d.deviceType,
            platform: d.platform,
            lastActive: d.lastActive,
            linkedAt: d.linkedAt
        }))
    });
}));

/**
 * @route   DELETE /api/devices/:deviceId
 * @desc    Unlink/logout a device
 * @access  Private
 */
router.delete('/:deviceId', authMiddleware, asyncHandler(async (req, res) => {
    const device = await LinkedDevice.findOne({
        deviceId: req.params.deviceId,
        user: req.user._id
    });
    
    if (!device) {
        throw new AppError('Device not found', 404);
    }
    
    device.isActive = false;
    await device.save();
    
    res.json({
        success: true,
        message: 'Device unlinked successfully'
    });
}));

/**
 * @route   PUT /api/devices/:deviceId/refresh
 * @desc    Refresh device session
 * @access  Private
 */
router.put('/:deviceId/refresh', asyncHandler(async (req, res) => {
    const { refreshToken } = req.body;
    
    if (!refreshToken) {
        throw new AppError('Refresh token required', 400);
    }
    
    // Verify refresh token
    let decoded;
    try {
        decoded = jwt.verify(refreshToken, process.env.JWT_SECRET);
    } catch (error) {
        throw new AppError('Invalid refresh token', 401);
    }
    
    const device = await LinkedDevice.findOne({
        deviceId: decoded.deviceId,
        user: decoded.userId,
        isActive: true
    });
    
    if (!device) {
        throw new AppError('Device not found or inactive', 404);
    }
    
    // Update last active
    await device.updateLastActive();
    
    // Generate new access token
    const accessToken = jwt.sign(
        { id: decoded.userId, deviceId: decoded.deviceId },
        process.env.JWT_SECRET,
        { expiresIn: '7d' }
    );
    
    res.json({
        success: true,
        token: accessToken
    });
}));

module.exports = router;

