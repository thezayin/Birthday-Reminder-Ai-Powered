package com.thezayin.data.whatsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast

class WhatsAppSender(private val context: Context) {
    private val TAG = "WhatsAppSender"

    fun sendWhatsAppMessage(
        name: String,
        phoneCountryCode: String,
        phoneNumber: String,
        birthdayMessage: String
    ) {
        val fullPhoneNumber = "+$phoneCountryCode$phoneNumber"
        val message = "Hi $name, $birthdayMessage"

        try {
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$fullPhoneNumber&text=${Uri.encode(message)}")
            val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                setPackage("com.whatsapp")
            }

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to open WhatsApp: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
