package com.akshar.messaging.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.akshar.messaging.MainActivity
import com.akshar.messaging.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        Log.d("AksharFCM", "Message received: ${remoteMessage.messageId}")
        
        // Handle different types of notifications based on data
        val notificationType = remoteMessage.data["type"]
        
        when (notificationType) {
            "status_update" -> {
                handleStatusUpdateNotification(remoteMessage)
            }
            "status_view" -> {
                handleStatusViewNotification(remoteMessage)
            }
            else -> {
                // Handle regular message notifications
                remoteMessage.notification?.let {
                    sendNotification(it.title ?: "New Message", it.body ?: "")
                }
            }
        }
    }
    
    private fun handleStatusUpdateNotification(remoteMessage: RemoteMessage) {
        val statusOwnerName = remoteMessage.data["statusOwnerName"] ?: "Someone"
        val statusType = remoteMessage.data["statusType"] ?: "TEXT"
        val statusContent = remoteMessage.data["statusContent"] ?: ""
        
        val title = "$statusOwnerName posted a status"
        val body = when (statusType) {
            "TEXT" -> statusContent.take(50)
            "MUSIC" -> "🎵 Music status"
            "VOICE" -> "🎤 Voice status"
            "IMAGE" -> "📷 Photo status"
            else -> "New status update"
        }
        
        sendNotification(title, body)
    }
    
    private fun handleStatusViewNotification(remoteMessage: RemoteMessage) {
        val viewerName = remoteMessage.data["viewerName"] ?: "Someone"
        
        sendNotification("Status Viewed", "$viewerName viewed your status")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        
        Log.d("AksharFCM", "New token: $token")
        
        // TODO: Send token to server
    }

    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "akshar_messaging_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_stat_notification) // Monochrome icon for status bar
            .setColor(resources.getColor(R.color.akshar_primary, null)) // Brand color
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Akshar Messaging",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
