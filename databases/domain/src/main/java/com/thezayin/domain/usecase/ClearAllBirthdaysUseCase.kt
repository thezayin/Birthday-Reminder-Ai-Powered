package com.thezayin.domain.usecase

import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface ClearAllBirthdaysUseCase : suspend () -> Flow<Response<Unit>>

class ClearAllBirthdaysUseCaseImpl(private val repository: BirthdayRepository) :
    ClearAllBirthdaysUseCase {
    override suspend fun invoke(): Flow<Response<Unit>> =
        repository.clearAllBirthdays()
}
