package com.thezayin.data.scheduler.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.framework.notification.NotificationReceiver
import java.util.Calendar

object AlarmScheduler {
    private const val TAG = "AlarmScheduler"
    fun scheduleBirthdayNotification(context: Context, birthday: BirthdayModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("name", birthday.name)
            putExtra("day", birthday.day)
            putExtra("month", birthday.month)
            // Add more extras if needed
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            birthday.id, // Use birthday ID as requestCode for uniqueness
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = birthday.notifyAt
        }

        // Set exact alarm
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun cancelBirthdayNotification(context: Context, birthday: BirthdayModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            birthday.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }

    fun scheduleBirthdayAlarm(context: Context, birthday: BirthdayModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("name", birthday.name)
            putExtra("day", birthday.day)
            putExtra("month", birthday.month)
            putExtra("id", birthday.id) // Ensure this is correctly set
            putExtra("year", birthday.year ?: 0) // If applicable
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            birthday.id, // Use unique and non-zero ID
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = birthday.notifyAt
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Ensure the alarm is set for a future time
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            return
        }

        // Set exact alarm
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

    }

    fun cancelBirthdayAlarm(context: Context, birthday: BirthdayModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            birthday.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}