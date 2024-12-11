package com.thezayin.domain.usecase

import com.thezayin.domain.model.CalculateModel
import com.thezayin.domain.repository.CalculateRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface CalculateUseCase : suspend (String, String) -> Flow<Response<CalculateModel>>

class CalculateUseCaseImpl(private val repository: CalculateRepository) : CalculateUseCase {
    override suspend fun invoke(
        birthDate: String,
        currentDate: String
    ): Flow<Response<CalculateModel>> =
        repository.calculate(birthDate, currentDate)
}