package com.thezayin.data.scheduler.sms

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast

class SmsService : Service() {
    private val TAG = "SmsService"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val birthdayId = intent?.getIntExtra("birthday_id", -1) ?: -1
        val name = intent?.getStringExtra("name") ?: "Friend"
        val phoneCountryCode = intent?.getStringExtra("phoneCountryCode") ?: ""
        val phoneNumber = intent?.getStringExtra("phoneNumber") ?: ""
        val birthdayMessage = intent?.getStringExtra("birthdayMessage") ?: "Happy Birthday! 🎉"

        if (birthdayId == -1 || phoneNumber.isEmpty()) {
            stopSelf()
            return START_NOT_STICKY
        }

        sendSms(name, phoneCountryCode, phoneNumber, birthdayMessage)

        stopSelf()
        return START_NOT_STICKY
    }

    private fun sendSms(name: String, countryCode: String, phoneNumber: String, message: String) {
        val fullPhoneNumber = "+$countryCode$phoneNumber"
        val smsManager = SmsManager.getDefault()

        val smsMessage = "Hi $name, $message"

        try {
            val parts = smsManager.divideMessage(smsMessage)
            smsManager.sendMultipartTextMessage(fullPhoneNumber, null, parts, null, null)
            Toast.makeText(this, "SMS sent to $fullPhoneNumber", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
