package com.thezayin.data.scheduler.alarm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.thezayin.values.R

class AlarmService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val CHANNEL_ID = "alarm_service_channel"
    private val TAG = "AlarmService"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        Log.d(TAG, "Service Created")
    }

    @SuppressLint("NewApi")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var action = intent?.action
        Log.d(TAG, "onStartCommand called with action: $action")

        when (action) {
            "STOP_ALARM" -> {
                Log.d(TAG, "Stop Alarm action received")
                stopAlarm()
            }

            else -> {
                // Initialize alarm
                val name = intent?.getStringExtra("name") ?: "Friend"
                val id = intent?.getIntExtra("id", -1) ?: -1 // Use -1 to detect invalid IDs

                if (id == -1) {
                    Log.e(TAG, "Invalid ID received: $id")
                    stopSelf()
                    return START_NOT_STICKY
                }

                Log.d(TAG, "Starting alarm for: $name with ID: $id")

                // Initialize MediaPlayer with the alarm sound
                mediaPlayer =
                    MediaPlayer.create(this, R.raw.happy_birthday)?.apply {
                        isLooping = true
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .build()
                        )
                        start()
                        Log.d(TAG, "MediaPlayer started")
                    }

                if (mediaPlayer == null) {
                    Log.e(TAG, "Failed to create MediaPlayer")
                    stopSelf()
                    return START_NOT_STICKY
                }

                // Create a notification with a stop action
                val stopIntent = Intent(this, AlarmService::class.java).apply {
                    action = "STOP_ALARM"
                }

                val stopPendingIntent = PendingIntent.getService(
                    this,
                    id, // Ensure this is unique and non-zero
                    stopIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Birthday Alarm")
                    .setContentText("It's $name's birthday today!")
                    .setSmallIcon(R.drawable.ic_main) // Ensure this drawable exists
                    .addAction(
                        R.drawable.ic_delete, // Icon for the stop action
                        "Stop Alarm",
                        stopPendingIntent
                    )
                    .setOngoing(true) // Makes the notification persistent
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build()

                // Start the service in the foreground with a unique notification ID and service type
                startForeground(
                    id,
                    notification,
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
                )
                Log.d(TAG, "Foreground service started with notification ID: $id")
            }
        }

        return START_NOT_STICKY
    }

    private fun stopAlarm() {
        Log.d(TAG, "Stopping alarm")
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                Log.d(TAG, "MediaPlayer stopped")
            }
            it.release()
            Log.d(TAG, "MediaPlayer released")
        }
        mediaPlayer = null
        stopForeground(true)
        stopSelf()
        Log.d(TAG, "Service stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service Destroyed")
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.stop()
                Log.d(TAG, "MediaPlayer stopped in onDestroy")
            }
            it.release()
            Log.d(TAG, "MediaPlayer released in onDestroy")
        }
        mediaPlayer = null
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Alarm Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel for Alarm Service"
                setSound(null, null) // Sound is handled by MediaPlayer
                enableLights(true)
                enableVibration(true)
            }

            val manager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
            Log.d(TAG, "Notification channel created")
        }
    }
}
