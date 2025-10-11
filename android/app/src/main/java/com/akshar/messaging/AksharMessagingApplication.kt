package com.akshar.messaging

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
// import androidx.work.Configuration
// import androidx.work.WorkManager
import com.google.firebase.FirebaseApp
// import dagger.hilt.android.HiltAndroidApp

class AksharMessagingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            Log.e("AksharApp", "Firebase initialization failed: ${e.message}")
        }
        
        // Initialize WorkManager - Temporarily disabled
        // WorkManager.initialize(
        //     this,
        //     Configuration.Builder()
        //         .setMinimumLoggingLevel(android.util.Log.DEBUG)
        //         .build()
        // )
        
        // Create notification channels
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Messages channel
            val messagesChannel = NotificationChannel(
                CHANNEL_MESSAGES,
                "Messages",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for new messages"
                enableVibration(true)
                enableLights(true)
                setShowBadge(true)
            }

            // Calls channel
            val callsChannel = NotificationChannel(
                CHANNEL_CALLS,
                "Calls",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for incoming calls"
                enableVibration(true)
                enableLights(true)
                setShowBadge(false)
            }

            // General channel
            val generalChannel = NotificationChannel(
                CHANNEL_GENERAL,
                "General",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "General notifications"
                enableVibration(false)
                enableLights(false)
                setShowBadge(false)
            }

            notificationManager.createNotificationChannels(
                listOf(messagesChannel, callsChannel, generalChannel)
            )
        }
    }

    companion object {
        const val CHANNEL_MESSAGES = "channel_messages"
        const val CHANNEL_CALLS = "channel_calls"
        const val CHANNEL_GENERAL = "channel_general"
    }
}
