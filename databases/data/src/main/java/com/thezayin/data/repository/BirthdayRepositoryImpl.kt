package com.thezayin.data.repository

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import com.thezayin.data.dao.BirthdayDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BirthdayRepositoryImpl(private val birthdayDao: BirthdayDao) : BirthdayRepository {

    override fun addBirthday(birthday: BirthdayModel): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)
            val result = birthdayDao.addBirthday(birthday)
            if (result > 0) {
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
                emit(Response.Success(Unit))
            } else {
                emit(Response.Error("Failed to update birthday"))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getBirthday(id: Int): Flow<Response<BirthdayModel?>> = flow {
        try {
            emit(Response.Loading)
            val birthday = birthdayDao.getBirthday(id)
            emit(Response.Success(birthday))
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

    override fun getBirthdayCount(): Flow<Response<Int>> = flow {
        try {
            emit(Response.Loading)
            val count = birthdayDao.getBirthdayCount()
            emit(Response.Success(count))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getBirthdayCountByGroup(group: String): Flow<Response<Int>> = flow {
        try {
            emit(Response.Loading)
            val count = birthdayDao.getBirthdayCountByGroup(group)
            emit(Response.Success(count))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getBirthdayCountByMonth(month: Int): Flow<Response<Int>> = flow {
        try {
            emit(Response.Loading)
            val count = birthdayDao.getBirthdayCountByMonth(month)
            emit(Response.Success(count))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getBirthdayCountByDay(day: Int): Flow<Response<Int>> = flow {
        try {
            emit(Response.Loading)
            val count = birthdayDao.getBirthdayCountByDay(day)
            emit(Response.Success(count))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun getBirthdayCountByYear(year: Int): Flow<Response<Int>> = flow {
        try {
            emit(Response.Loading)
            val count = birthdayDao.getBirthdayCountByYear(year)
            emit(Response.Success(count))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}
