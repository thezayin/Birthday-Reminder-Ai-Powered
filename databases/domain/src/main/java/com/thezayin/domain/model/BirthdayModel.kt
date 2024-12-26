package com.thezayin.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "birthday_table")
@Serializable
data class BirthdayModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val day: Int,
    val month: Int,
    val year: Int?, // Make year optional if not always provided
    val group: String,
    val notifyAt: Long,// Timestamp for notification
    val phoneCountryCode: String? = null,
    val phoneNumber: String? = null,
    val notificationMethod: String? = null, // "Text" or "WhatsApp"
    val birthdayMessage: String? = null
)