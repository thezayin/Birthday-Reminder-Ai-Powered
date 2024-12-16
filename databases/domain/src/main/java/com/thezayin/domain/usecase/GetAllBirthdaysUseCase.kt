package com.thezayin.domain.usecase

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface GetAllBirthdaysUseCase : suspend () -> Flow<Response<List<BirthdayModel>>>

class GetAllBirthdaysUseCaseImpl(private val repository: BirthdayRepository) :
    GetAllBirthdaysUseCase {
    override suspend fun invoke(): Flow<Response<List<BirthdayModel>>> =
        repository.getAllBirthdays()
}
