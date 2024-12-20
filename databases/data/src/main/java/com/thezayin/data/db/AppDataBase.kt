package com.thezayin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thezayin.data.dao.BirthdayDao
import com.thezayin.data.dao.CalDao
import com.thezayin.domain.model.AgeCalModel
import com.thezayin.domain.model.BirthdayModel


@Database(
    entities = [AgeCalModel::class, BirthdayModel::class],
    version = 3, // Incremented version due to schema change
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun calHistoryDao(): CalDao
    abstract fun birthdayDao(): BirthdayDao
}