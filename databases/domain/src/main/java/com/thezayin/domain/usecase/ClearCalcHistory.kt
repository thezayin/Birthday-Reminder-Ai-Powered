package com.thezayin.domain.usecase

import com.thezayin.domain.repository.CalcDBRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ClearCalcHistory : suspend () -> Flow<Response<Boolean>>

class ClearCalcHistoryImpl(private val repository: CalcDBRepository) : ClearCalcHistory {
    override suspend fun invoke(): Flow<Response<Boolean>> =
        repository.clearCalHistory()
}