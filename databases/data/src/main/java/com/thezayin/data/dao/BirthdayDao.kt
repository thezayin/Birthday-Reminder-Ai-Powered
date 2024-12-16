package com.thezayin.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thezayin.domain.model.BirthdayModel

@Dao
interface BirthdayDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBirthday(birthday: BirthdayModel): Long

    @Delete
    suspend fun deleteBirthday(birthday: BirthdayModel): Int

    @Update
    suspend fun updateBirthday(birthday: BirthdayModel): Int

    @Query("SELECT * FROM birthday_table WHERE id = :id")
    suspend fun getBirthday(id: Int): BirthdayModel?

    @Query("SELECT * FROM birthday_table ORDER BY month, day ASC")
    suspend fun getAllBirthdays(): List<BirthdayModel>

    @Query("DELETE FROM birthday_table")
    suspend fun clearAllBirthdays(): Int

    @Query("SELECT COUNT(*) FROM birthday_table")
    suspend fun getBirthdayCount(): Int

    @Query("SELECT COUNT(*) FROM birthday_table WHERE `group` = :group")
    suspend fun getBirthdayCountByGroup(group: String): Int

    @Query("SELECT COUNT(*) FROM birthday_table WHERE month = :month")
    suspend fun getBirthdayCountByMonth(month: Int): Int

    @Query("SELECT COUNT(*) FROM birthday_table WHERE day = :day")
    suspend fun getBirthdayCountByDay(day: Int): Int

    @Query("SELECT COUNT(*) FROM birthday_table WHERE year = :year")
    suspend fun getBirthdayCountByYear(year: Int): Int
}
