package com.thezayin.domain.usecase

import com.thezayin.domain.model.AgeCalModel
import com.thezayin.domain.repository.CalcDBRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetCalHistory : suspend () -> Flow<Response<List<AgeCalModel>>>

class GetCalHistoryImpl(private val repository: CalcDBRepository) : GetCalHistory {
    override suspend fun invoke(): Flow<Response<List<AgeCalModel>>> =
        repository.getAllCalHistory()
}