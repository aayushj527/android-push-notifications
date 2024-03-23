package com.test.pushnotifications.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.pushnotifications.R

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        try {
            val notificationManager =
                this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val notificationBuilder = if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                NotificationCompat.Builder(this, "test_channel")
            } else {
                NotificationCompat.Builder(this)
            }

            notificationBuilder
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(message.notification?.title)
                .setStyle(NotificationCompat.BigTextStyle().bigText(message.notification?.body))

            if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
                notificationManager.createNotificationChannelIfNOtExists(
                    channelId = "test_channel",
                    channelName = "test_channel_name",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            }

            notificationManager.notify(
                120836789,
                notificationBuilder.build()
            )
        } catch (e: Exception) {
            Log.d("FCM Token", e.localizedMessage)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun NotificationManager.createNotificationChannelIfNOtExists(
        channelId: String,
        channelName: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ) {
        var channel = this.getNotificationChannel(channelId)

        if (channel == null) {
            channel = NotificationChannel(
                channelId,
                channelName,
                importance
            )
            this.createNotificationChannel(channel)
        }
    }
}