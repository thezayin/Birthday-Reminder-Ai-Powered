package com.thezayin.data.di

import android.content.Context
import androidx.room.Room
import com.thezayin.data.db.AppDatabase


fun provideDatabase(context: Context) =
    Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

fun provideHistoryDao(database: AppDatabase) = database.calHistoryDao()