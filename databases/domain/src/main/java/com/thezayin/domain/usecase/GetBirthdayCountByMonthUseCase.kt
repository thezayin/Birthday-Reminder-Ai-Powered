package com.thezayin.domain.usecase

import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetBirthdayCountByMonthUseCase : suspend (Int) -> Flow<Response<Int>>

class GetBirthdayCountByMonthUseCaseImpl(private val repository: BirthdayRepository) :
    GetBirthdayCountByMonthUseCase {
    override suspend fun invoke(month: Int): Flow<Response<Int>> =
        repository.getBirthdayCountByMonth(month)
}