package com.domikado.bit.abstraction.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.domikado.bit.R
import com.domikado.bit.ui.screen.MainActivity

object NotificationHelper {

    /**
     * Caution: If you target Android 8.0 (API level 26) and post a notification without specifying a notification channel,
     * the notification does not appear and the system logs an error.
     */
    fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // for above API 26
            val channelId = getChannelId(name)
            val channel = NotificationChannel(channelId, name, importance).apply {
                this.description = description
                this.setShowBadge(showBadge)
                this.setSound(
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                        .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
                )
                this.vibrationPattern = longArrayOf(100, 100, 100, 100)
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun deleteNotificationChannel(context: Context, name: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getChannelId(name)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.deleteNotificationChannel(channelId)
        }
    }

    fun notifyRejection(context: Context, name: String, title: String?, message: String?) {
        val notificationId = 2
        val requestCode = 2
        val channelId = getChannelId(name)
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_notification_bit)
            setContentTitle(title?: "Rejection")
            setContentText(message?: "Terdapat rejection")
            priority = NotificationCompat.PRIORITY_DEFAULT
            setCategory(NotificationCompat.CATEGORY_MESSAGE)
            setAutoCancel(true)

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val bundle = Bundle()
            bundle.putString(EXTRA_KEY_REJECTION, "notification rejection")
            val pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT, bundle)
            setContentIntent(pendingIntent)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun getChannelId(name: String) = "channelId-$name"

    internal val DEFAULT_NAME = "default"
    internal val EXTRA_KEY_REJECTION = "EXTRA_KEY_REJECTION"
}