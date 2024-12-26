package com.thezayin.data.repository

import android.content.Context
import com.thezayin.data.dao.BirthdayDao
import com.thezayin.data.scheduler.alarm.AlarmScheduler
import com.thezayin.data.scheduler.sms.SmsScheduler
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BirthdayRepositoryImpl(private val birthdayDao: BirthdayDao, private val context: Context) :
    BirthdayRepository {

    override fun addBirthday(birthday: BirthdayModel): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            val result = birthdayDao.addBirthday(birthday)
            if (result > 0) {
                val updatedBirthday = birthday.copy(id = result.toInt())
                AlarmScheduler.scheduleBirthdayAlarm(context, updatedBirthday)
                AlarmScheduler.scheduleBirthdayNotification(context, updatedBirthday)
                emit(Response.Success(Unit))
            } else {
                emit(Response.Error("Failed to add birthday"))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun deleteBirthday(birthday: BirthdayModel): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            AlarmScheduler.cancelBirthdayAlarm(context, birthday)
            AlarmScheduler.cancelBirthdayNotification(context, birthday)
            val rowsDeleted = birthdayDao.deleteBirthday(birthday)
            if (rowsDeleted > 0) {
                emit(Response.Success(Unit))
            } else {
                emit(Response.Error("Failed to delete birthday"))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun updateBirthday(birthday: BirthdayModel): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            val rowsUpdated = birthdayDao.updateBirthday(birthday)
            if (rowsUpdated > 0) {
                if (birthday.notificationMethod == "Text") {
                    SmsScheduler.cancelSms(context, birthday)
                }
                AlarmScheduler.cancelBirthdayNotification(context, birthday)
                AlarmScheduler.cancelBirthdayAlarm(context, birthday)
                AlarmScheduler.scheduleBirthdayNotification(context, birthday)
                AlarmScheduler.scheduleBirthdayAlarm(context, birthday)
                emit(Response.Success(Unit))
            } else {
                emit(Response.Error("Failed to update birthday"))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getAllBirthdays(): Flow<Response<List<BirthdayModel>>> = flow {
        try {
            emit(Response.Loading)
            val birthdays = birthdayDao.getAllBirthdays()
            emit(Response.Success(birthdays))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun clearAllBirthdays(): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            val birthdays = birthdayDao.getAllBirthdays()
            birthdays.forEach {
                AlarmScheduler.cancelBirthdayAlarm(context, it)
                AlarmScheduler.cancelBirthdayNotification(context, it)
            }
            val rowsDeleted = birthdayDao.clearAllBirthdays()
            if (rowsDeleted > 0) {
                emit(Response.Success(Unit))
            } else {
                emit(Response.Error("Failed to clear birthdays"))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun scheduleBirthdayNotification(
        context: Context,
        birthday: BirthdayModel
    ): Flow<Response<Unit>> = flow {
        try {
            AlarmScheduler.scheduleBirthdayAlarm(context, birthday)
            AlarmScheduler.scheduleBirthdayNotification(context, birthday)
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Failed to schedule notification"))
        }
    }

    override fun cancelBirthdayNotification(
        context: Context,
        birthday: BirthdayModel
    ): Flow<Response<Unit>> = flow {
        try {
            AlarmScheduler.cancelBirthdayAlarm(context, birthday)
            AlarmScheduler.cancelBirthdayNotification(context, birthday)
            emit(Response.Success(Unit))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Failed to cancel notification"))
        }
    }
}
