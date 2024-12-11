package com.thezayin.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calc_table")
@kotlinx.serialization.Serializable
data class AgeCalModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String? = "",
    val years: String? = "",
    val months: String? = "",
    val days: String? = "",
)