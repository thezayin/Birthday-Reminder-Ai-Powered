package com.thezayin.domain.repository

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface BirthdayRepository {
    fun addBirthday(birthday: BirthdayModel): Flow<Response<Unit>>
    fun deleteBirthday(birthday: BirthdayModel): Flow<Response<Unit>>
    fun updateBirthday(birthday: BirthdayModel): Flow<Response<Unit>>

    fun getBirthday(id: Int): Flow<Response<BirthdayModel?>>
    fun getAllBirthdays(): Flow<Response<List<BirthdayModel>>>

    fun clearAllBirthdays(): Flow<Response<Unit>>

    fun getBirthdayCount(): Flow<Response<Int>>
    fun getBirthdayCountByGroup(group: String): Flow<Response<Int>>
    fun getBirthdayCountByMonth(month: Int): Flow<Response<Int>>
    fun getBirthdayCountByDay(day: Int): Flow<Response<Int>>
    fun getBirthdayCountByYear(year: Int): Flow<Response<Int>>
}
