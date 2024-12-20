package com.thezayin.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thezayin.data.db.AppDatabase


val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create the birthday_table with all fields
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `birthday_table` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `name` TEXT NOT NULL,
                `day` INTEGER NOT NULL,
                `month` INTEGER NOT NULL,
                `year` INTEGER,
                `group` TEXT NOT NULL,
                `notifyAt` INTEGER NOT NULL,
                `phoneCountryCode` TEXT,
                `phoneNumber` TEXT,
                `notificationMethod` TEXT,
                `birthdayMessage` TEXT
            )
            """.trimIndent()
        )
    }
}

fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
        .addMigrations(MIGRATION_1_2)
        .fallbackToDestructiveMigration() // Consider removing if handling migrations properly
        .build()

fun provideHistoryDao(database: AppDatabase) = database.calHistoryDao()
fun provideBirthdayDao(database: AppDatabase) = database.birthdayDao()