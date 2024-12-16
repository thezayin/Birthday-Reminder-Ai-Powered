package com.thezayin.domain.usecase

import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetBirthdayCountByGroupUseCase : suspend (String) -> Flow<Response<Int>>

class GetBirthdayCountByGroupUseCaseImpl(private val repository: BirthdayRepository) :
    GetBirthdayCountByGroupUseCase {
    override suspend fun invoke(group: String): Flow<Response<Int>> =
        repository.getBirthdayCountByGroup(group)
}