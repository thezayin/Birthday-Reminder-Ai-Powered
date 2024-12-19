package com.thezayin.presentation.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.thezayin.data.alarm.AlarmScheduler
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val repository: BirthdayRepository = GlobalContext.get().get()

            CoroutineScope(Dispatchers.IO).launch {
                repository.getAllBirthdays().collect { response ->
                    if (response is Response.Success) {
                        response.data.forEach { birthday ->
                            AlarmScheduler.scheduleBirthdayAlarm(context, birthday)
                            AlarmScheduler.scheduleBirthdayNotification(context, birthday)
                        }
                        Log.d("BootReceiver", "Alarms rescheduled for all birthdays.")
                    } else if (response is Response.Error) {
                        Log.e("BootReceiver", "Error fetching birthdays: ${response.e}")
                    }
                }
            }
        }
    }
}