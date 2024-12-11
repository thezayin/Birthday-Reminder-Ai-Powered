package com.thezayin.data.repository

import com.thezayin.data.dao.CalDao
import com.thezayin.domain.model.AgeCalModel
import com.thezayin.domain.repository.CalcDBRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CalcDBRepositoryImpl(private val dao: CalDao) : CalcDBRepository {
    override fun getAllCalHistory(): Flow<Response<List<AgeCalModel>>> = flow {
        try {
            emit(Response.Loading)
            val history = dao.getAllCalHistory()
            emit(Response.Success(history))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun insertCalHistory(ageCalModel: AgeCalModel): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            val id = dao.insertCalHistory(ageCalModel)
            emit(Response.Success(id > 0))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }

    override fun clearCalHistory(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            val rowsDeleted = dao.clearCalHistory()
            emit(Response.Success(rowsDeleted > 0))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An error occurred"))
        }
    }
}