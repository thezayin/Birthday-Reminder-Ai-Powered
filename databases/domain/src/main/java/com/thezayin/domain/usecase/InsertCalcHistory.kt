package com.thezayin.domain.usecase

import com.thezayin.domain.model.AgeCalModel
import com.thezayin.domain.repository.CalcDBRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface InsertCalcHistory : suspend (String, String, String, String) -> Flow<Response<Boolean>>

class InsertCalcHistoryImpl(private val repository: CalcDBRepository) : InsertCalcHistory {
    override suspend fun invoke(
        name: String,
        years: String,
        months: String,
        days: String
    ): Flow<Response<Boolean>> =
        repository.insertCalHistory(
            AgeCalModel(
                name = name,
                years = years,
                months = months,
                days = days
            )
        )
}