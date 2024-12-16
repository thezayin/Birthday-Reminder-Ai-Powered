package com.thezayin.domain.usecase

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetBirthdayUseCase : suspend (Int) -> Flow<Response<BirthdayModel?>>

class GetBirthdayUseCaseImpl(private val repository: BirthdayRepository) : GetBirthdayUseCase {
    override suspend fun invoke(id: Int): Flow<Response<BirthdayModel?>> =
        repository.getBirthday(id)
}