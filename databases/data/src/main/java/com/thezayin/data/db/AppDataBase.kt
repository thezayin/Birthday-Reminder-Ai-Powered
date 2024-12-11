package com.thezayin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thezayin.data.dao.CalDao
import com.thezayin.domain.model.AgeCalModel

/**
 * Room database class for the application.
 */
@Database(
    entities = [AgeCalModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun calHistoryDao(): CalDao
}