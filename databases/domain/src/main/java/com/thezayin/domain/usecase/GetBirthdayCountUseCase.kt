package com.thezayin.domain.usecase

import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetBirthdayCountUseCase : suspend () -> Flow<Response<Int>>

class GetBirthdayCountUseCaseImpl(private val repository: BirthdayRepository) :
    GetBirthdayCountUseCase {
    override suspend fun invoke(): Flow<Response<Int>> =
        repository.getBirthdayCount()
}