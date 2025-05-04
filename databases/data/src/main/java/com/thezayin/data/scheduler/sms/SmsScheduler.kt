package com.thezayin.data.scheduler.sms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.thezayin.domain.model.BirthdayModel
import java.util.Calendar

object SmsScheduler {
    private const val TAG = "SmsScheduler"

    fun scheduleSms(context: Context, birthday: BirthdayModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, SmsAlarmReceiver::class.java).apply {
            putExtra("birthday_id", birthday.id)
            putExtra("name", birthday.name)
            putExtra("phoneCountryCode", birthday.phoneCountryCode)
            putExtra("phoneNumber", birthday.phoneNumber)
            putExtra("birthdayMessage", birthday.birthdayMessage)
            putExtra("notificationMethod", birthday.notificationMethod) // Added notificationMethod
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            birthday.id, // Unique request code
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

        // Schedule the exact alarm
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

    }

    fun cancelSms(context: Context, birthday: BirthdayModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, SmsAlarmReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            birthday.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}
