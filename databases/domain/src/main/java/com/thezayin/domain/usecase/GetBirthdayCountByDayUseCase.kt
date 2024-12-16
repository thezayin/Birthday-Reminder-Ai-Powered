package com.thezayin.domain.usecase

import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetBirthdayCountByDayUseCase : suspend (Int) -> Flow<Response<Int>>

class GetBirthdayCountByDayUseCaseImpl(private val repository: BirthdayRepository) : GetBirthdayCountByDayUseCase {
    override suspend fun invoke(day: Int): Flow<Response<Int>> =
        repository.getBirthdayCountByDay(day)
}