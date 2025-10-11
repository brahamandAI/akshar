const admin = require('firebase-admin');

// Initialize Firebase Admin SDK
if (!admin.apps.length && process.env.FIREBASE_PROJECT_ID && process.env.FIREBASE_PRIVATE_KEY && process.env.FIREBASE_CLIENT_EMAIL) {
  try {
    admin.initializeApp({
      credential: admin.credential.cert({
        projectId: process.env.FIREBASE_PROJECT_ID,
        privateKey: process.env.FIREBASE_PRIVATE_KEY?.replace(/\\n/g, '\n'),
        clientEmail: process.env.FIREBASE_CLIENT_EMAIL,
      }),
    });
    console.log('✅ Firebase Admin SDK initialized successfully');
  } catch (error) {
    console.warn('⚠️ Firebase Admin SDK initialization failed:', error.message);
  }
}

const messaging = admin.apps.length > 0 ? admin.messaging() : null;

/**
 * Send push notification to a single device
 */
const sendToDevice = async (deviceToken, notification, data = {}) => {
  try {
    if (!messaging) {
      console.warn('⚠️ Firebase messaging not available, skipping notification');
      return { success: false, error: 'Firebase not configured' };
    }
    const message = {
      token: deviceToken,
      notification: {
        title: notification.title,
        body: notification.body,
        imageUrl: notification.imageUrl
      },
      data: {
        ...data,
        click_action: 'FLUTTER_NOTIFICATION_CLICK'
      },
      android: {
        notification: {
          icon: 'ic_notification',
          color: '#2196F3',
          sound: 'default',
          channelId: 'messages',
          priority: 'high',
          visibility: 'private'
        },
        data: {
          ...data
        }
      },
      apns: {
        payload: {
          aps: {
            alert: {
              title: notification.title,
              body: notification.body
            },
            sound: 'default',
            badge: 1,
            mutableContent: true
          }
        },
        headers: {
          'apns-priority': '10',
          'apns-expiration': Math.floor(Date.now() / 1000) + 3600 // 1 hour
        }
      }
    };

    const response = await messaging.send(message);
    return {
      success: true,
      messageId: response,
      deviceToken
    };
  } catch (error) {
    console.error('Error sending notification to device:', error);
    return {
      success: false,
      error: error.message,
      deviceToken
    };
  }
};

/**
 * Send push notification to multiple devices
 */
const sendToMultipleDevices = async (deviceTokens, notification, data = {}) => {
  try {
    const message = {
      tokens: deviceTokens,
      notification: {
        title: notification.title,
        body: notification.body,
        imageUrl: notification.imageUrl
      },
      data: {
        ...data,
        click_action: 'FLUTTER_NOTIFICATION_CLICK'
      },
      android: {
        notification: {
          icon: 'ic_notification',
          color: '#2196F3',
          sound: 'default',
          channelId: 'messages',
          priority: 'high',
          visibility: 'private'
        }
      },
      apns: {
        payload: {
          aps: {
            alert: {
              title: notification.title,
              body: notification.body
            },
            sound: 'default',
            badge: 1,
            mutableContent: true
          }
        }
      }
    };

    const response = await messaging.sendMulticast(message);
    return {
      success: true,
      successCount: response.successCount,
      failureCount: response.failureCount,
      responses: response.responses
    };
  } catch (error) {
    console.error('Error sending multicast notification:', error);
    return {
      success: false,
      error: error.message
    };
  }
};

/**
 * Send message notification
 */
const sendMessageNotification = async (chat, message, sender, recipients) => {
  try {
    const notification = {
      title: chat.isGroup ? 
        `${sender.firstName} ${sender.lastName} in ${chat.groupName}` : 
        `${sender.firstName} ${sender.lastName}`,
      body: getMessagePreview(message),
      imageUrl: sender.avatar
    };

    const data = {
      type: 'message',
      chatId: chat._id.toString(),
      messageId: message._id.toString(),
      senderId: sender._id.toString(),
      senderName: `${sender.firstName} ${sender.lastName}`,
      isGroup: chat.isGroup.toString(),
      groupName: chat.isGroup ? chat.groupName : '',
      messageType: message.type,
      timestamp: message.createdAt.toISOString()
    };

    // Get device tokens for all recipients
    const deviceTokens = [];
    for (const recipient of recipients) {
      if (recipient.deviceTokens && recipient.deviceTokens.length > 0) {
        deviceTokens.push(...recipient.deviceTokens.map(dt => dt.token));
      }
    }

    if (deviceTokens.length === 0) {
      return { success: true, message: 'No device tokens found' };
    }

    return await sendToMultipleDevices(deviceTokens, notification, data);
  } catch (error) {
    console.error('Error sending message notification:', error);
    return { success: false, error: error.message };
  }
};

/**
 * Send call notification
 */
const sendCallNotification = async (chat, caller, callType, recipients) => {
  try {
    const notification = {
      title: 'Incoming Call',
      body: `${caller.firstName} ${caller.lastName} is calling you`,
      imageUrl: caller.avatar
    };

    const data = {
      type: 'call',
      chatId: chat._id.toString(),
      callerId: caller._id.toString(),
      callerName: `${caller.firstName} ${caller.lastName}`,
      callType: callType, // 'voice' or 'video'
      timestamp: new Date().toISOString()
    };

    // Get device tokens for all recipients
    const deviceTokens = [];
    for (const recipient of recipients) {
      if (recipient.deviceTokens && recipient.deviceTokens.length > 0) {
        deviceTokens.push(...recipient.deviceTokens.map(dt => dt.token));
      }
    }

    if (deviceTokens.length === 0) {
      return { success: true, message: 'No device tokens found' };
    }

    return await sendToMultipleDevices(deviceTokens, notification, data);
  } catch (error) {
    console.error('Error sending call notification:', error);
    return { success: false, error: error.message };
  }
};

/**
 * Send status view notification to status owner
 */
const sendStatusViewNotification = async (statusOwner, viewer, statusId) => {
  try {
    const notification = {
      title: `${viewer.name} viewed your status`,
      body: 'Someone viewed your status update',
      imageUrl: viewer.profilePicture
    };
    
    const data = {
      type: 'status_view',
      statusId: statusId,
      viewerId: viewer._id.toString(),
      viewerName: viewer.name,
      timestamp: new Date().toISOString()
    };
    
    if (statusOwner.fcmToken) {
      await sendToDevice(statusOwner.fcmToken, notification, data);
    }
    
  } catch (error) {
    console.error('Error sending status view notification:', error);
  }
};

/**
 * Send group notification
 */
const sendGroupNotification = async (chat, action, user, recipients) => {
  try {
    let notification;
    
    switch (action) {
      case 'user_joined':
        notification = {
          title: chat.groupName,
          body: `${user.firstName} ${user.lastName} joined the group`,
          imageUrl: user.avatar
        };
        break;
      case 'user_left':
        notification = {
          title: chat.groupName,
          body: `${user.firstName} ${user.lastName} left the group`,
          imageUrl: user.avatar
        };
        break;
      case 'group_renamed':
        notification = {
          title: 'Group Info',
          body: `${user.firstName} ${user.lastName} renamed the group to "${chat.groupName}"`,
          imageUrl: user.avatar
        };
        break;
      case 'admin_added':
        notification = {
          title: chat.groupName,
          body: `${user.firstName} ${user.lastName} is now an admin`,
          imageUrl: user.avatar
        };
        break;
      default:
        return { success: false, error: 'Unknown group action' };
    }

    const data = {
      type: 'group',
      chatId: chat._id.toString(),
      action: action,
      userId: user._id.toString(),
      userName: `${user.firstName} ${user.lastName}`,
      timestamp: new Date().toISOString()
    };

    // Get device tokens for all recipients
    const deviceTokens = [];
    for (const recipient of recipients) {
      if (recipient.deviceTokens && recipient.deviceTokens.length > 0) {
        deviceTokens.push(...recipient.deviceTokens.map(dt => dt.token));
      }
    }

    if (deviceTokens.length === 0) {
      return { success: true, message: 'No device tokens found' };
    }

    return await sendToMultipleDevices(deviceTokens, notification, data);
  } catch (error) {
    console.error('Error sending group notification:', error);
    return { success: false, error: error.message };
  }
};

/**
 * Send status update notification
 */
const sendStatusNotification = async (user, statusType, statusContent, contactTokens) => {
  try {
    const statusTypeText = {
      'TEXT': 'text status',
      'MUSIC': 'music status', 
      'LAYOUT': 'status update',
      'VOICE': 'voice status',
      'IMAGE': 'photo status'
    }[statusType] || 'status update';
    
    const notification = {
      title: `${user.name} posted a ${statusTypeText}`,
      body: statusType === 'TEXT' ? statusContent.substring(0, 50) + '...' : 
            statusType === 'MUSIC' ? `🎵 ${statusContent}` :
            statusType === 'VOICE' ? '🎤 Voice status' :
            statusType === 'IMAGE' ? '📷 Photo status' :
            'New status update',
      imageUrl: user.profilePicture
    };
    
    const data = {
      type: 'status_update',
      statusType: statusType,
      statusOwnerId: user._id.toString(),
      statusOwnerName: user.name,
      statusContent: statusContent,
      timestamp: new Date().toISOString()
    };
    
    if (contactTokens.length > 0) {
      return await sendToMultipleDevices(contactTokens, notification, data);
    }
    
    return { success: true };
  } catch (error) {
    console.error('Error sending status notification:', error);
    return { success: false, error: error.message };
  }
};

/**
 * Get message preview text
 */
const getMessagePreview = (message) => {
  if (message.isDeleted) {
    return 'This message was deleted';
  }

  switch (message.type) {
    case 'text':
      return message.content.length > 50 ? 
        message.content.substring(0, 50) + '...' : 
        message.content;
    case 'image':
      return '📷 Photo';
    case 'video':
      return '🎥 Video';
    case 'audio':
      return '🎵 Audio';
    case 'document':
      return '📄 Document';
    case 'location':
      return '📍 Location';
    case 'contact':
      return '👤 Contact';
    case 'sticker':
      return '😀 Sticker';
    case 'reply':
      return `↩️ ${message.content}`;
    case 'forward':
      return `↗️ ${message.content}`;
    default:
      return 'New message';
  }
};

/**
 * Validate FCM token
 */
const validateToken = async (token) => {
  try {
    const message = {
      token: token,
      data: {
        test: 'validation'
      }
    };

    await messaging.send(message);
    return true;
  } catch (error) {
    console.error('Token validation failed:', error);
    return false;
  }
};

/**
 * Subscribe to topic
 */
const subscribeToTopic = async (tokens, topic) => {
  try {
    const response = await messaging.subscribeToTopic(tokens, topic);
    return {
      success: true,
      response
    };
  } catch (error) {
    console.error('Error subscribing to topic:', error);
    return {
      success: false,
      error: error.message
    };
  }
};

/**
 * Unsubscribe from topic
 */
const unsubscribeFromTopic = async (tokens, topic) => {
  try {
    const response = await messaging.unsubscribeFromTopic(tokens, topic);
    return {
      success: true,
      response
    };
  } catch (error) {
    console.error('Error unsubscribing from topic:', error);
    return {
      success: false,
      error: error.message
    };
  }
};

module.exports = {
  sendToDevice,
  sendToMultipleDevices,
  sendMessageNotification,
  sendCallNotification,
  sendGroupNotification,
  sendStatusNotification,
  sendStatusViewNotification,
  validateToken,
  subscribeToTopic,
  unsubscribeFromTopic,
  getMessagePreview
};
