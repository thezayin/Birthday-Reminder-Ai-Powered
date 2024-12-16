package com.thezayin.domain.usecase

import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetBirthdayCountByYearUseCase : suspend (Int) -> Flow<Response<Int>>

class GetBirthdayCountByYearUseCaseImpl(private val repository: BirthdayRepository) : GetBirthdayCountByYearUseCase {
    override suspend fun invoke(year: Int): Flow<Response<Int>> =
        repository.getBirthdayCountByYear(year)
}