package com.thezayin.data.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {
    private val TAG = "AlarmReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        val name = intent.getStringExtra("name") ?: "Friend"
        val id = intent.getIntExtra("id", 0)
        val day = intent.getIntExtra("day", 1)
        val month = intent.getIntExtra("month", 1)
        val year = intent.getIntExtra("year", 0) // If applicable

        Log.d(TAG, "Alarm triggered for: $name (ID: $id)")

        // Start AlarmService to handle the alarm sound
        val serviceIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("name", name)
            putExtra("id", id)
            putExtra("day", day)
            putExtra("month", month)
            putExtra("year", year)
        }

        ContextCompat.startForegroundService(context, serviceIntent)
        Log.d(TAG, "AlarmService started for ID: $id")
    }
}