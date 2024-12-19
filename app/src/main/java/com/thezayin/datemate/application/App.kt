package com.thezayin.datemate.application

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.thezayin.add_birthday.di.addBirthdayModule
import com.thezayin.di.analyticsModule
import com.thezayin.framework.di.featureModule
import com.thezayin.framework.notification.NotificationUtil
import com.thezayin.presentation.di.calculatorModule
import com.thezayin.presentation.di.databaseModule
import com.thezayin.presentation.di.giftIdeasModule
import com.thezayin.presentation.di.homeModule
import com.thezayin.saved_birthdays.di.savedBirthdayModule
import com.thezayin.setting.di.settingModule
import com.thezayin.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationUtil.createNotificationChannel(this)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                homeModule,
                featureModule,
                splashModule,
                settingModule,
                com.thezayin.di.analyticsModule,
                databaseModule,
                calculatorModule,
                addBirthdayModule,
                savedBirthdayModule,
                giftIdeasModule
            )
        }
        // Initialize Firebase Crashlytics
        val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

//        // Initialize and start GrizzlyMonitor with custom settings
//        GrizzlyMonitorBuilder(this)
//            .withTicker(200L) // Set ticker interval (1-500ms)
//            .withThreshold(3000L) // Set ANR threshold (1000-4500ms)
//            .withTitle("App Error") // Set custom crash dialog title
//            .withMessage("An error occurred. Please restart.") // Set custom crash dialog message
//            .withFirebaseCrashLytics(firebaseCrashlytics) // Integrate with Firebase Crashlytics
//            .build()
//            .start()
    }
}