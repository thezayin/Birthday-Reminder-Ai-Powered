package com.thezayin.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thezayin.domain.model.AgeCalModel

@Dao
interface CalDao {
    @Query("SELECT * FROM calc_table ORDER BY id DESC")
    fun getAllCalHistory(): List<AgeCalModel>

    /**
     * Deletes all entries from the search history table.
     */
    @Query("DELETE FROM calc_table")
    suspend fun clearCalHistory(): Int

    /**
     * Inserts a new history entry. Ignores the insert if there's a conflict.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCalHistory(calModel: AgeCalModel): Long
}