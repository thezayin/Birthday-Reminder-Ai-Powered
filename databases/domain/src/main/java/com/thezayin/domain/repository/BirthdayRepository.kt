package com.thezayin.domain.repository

import android.content.Context
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface BirthdayRepository {
    fun addBirthday(birthday: BirthdayModel): Flow<Response<Unit>>
    fun deleteBirthday(birthday: BirthdayModel): Flow<Response<Unit>>
    fun updateBirthday(birthday: BirthdayModel): Flow<Response<Unit>>
    fun getAllBirthdays(): Flow<Response<List<BirthdayModel>>>
    fun clearAllBirthdays(): Flow<Response<Unit>>
    fun scheduleBirthdayNotification(context: Context, birthday: BirthdayModel): Flow<Response<Unit>>
    fun cancelBirthdayNotification(context: Context, birthday: BirthdayModel): Flow<Response<Unit>>
}
