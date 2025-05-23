package com.thezayin.data.scheduler.sms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.thezayin.data.whatsapp.WhatsAppSender

class SmsAlarmReceiver : BroadcastReceiver() {
    private val TAG = "SmsAlarmReceiver"

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == null) {
            return
        }

        val birthdayId = intent.getIntExtra("birthday_id", -1)
        val name = intent.getStringExtra("name") ?: "Friend"
        val phoneCountryCode = intent.getStringExtra("phoneCountryCode") ?: ""
        val phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        val birthdayMessage = intent.getStringExtra("birthdayMessage") ?: "Happy Birthday! 🎉"
        val notificationMethod =
            intent.getStringExtra("notificationMethod") ?: "Text" // Default to Text

        if (birthdayId == -1 || phoneNumber.isEmpty()) {
            return
        }

        when (notificationMethod) {
            "Text" -> {
                // Start the SmsService to send the SMS
                val serviceIntent = Intent(context, SmsService::class.java).apply {
                    putExtra("birthday_id", birthdayId)
                    putExtra("name", name)
                    putExtra("phoneCountryCode", phoneCountryCode)
                    putExtra("phoneNumber", phoneNumber)
                    putExtra("birthdayMessage", birthdayMessage)
                }
                context.startService(serviceIntent)
            }

            "WhatsApp" -> {
                // Send WhatsApp message
                val whatsAppSender = WhatsAppSender(context)
                whatsAppSender.sendWhatsAppMessage(
                    name = name,
                    phoneCountryCode = phoneCountryCode,
                    phoneNumber = phoneNumber,
                    birthdayMessage = birthdayMessage
                )
            }

            else -> {
            }
        }
    }
}
