package com.thezayin.domain.usecase

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface DeleteBirthdayUseCase {
    suspend operator fun invoke(birthday: BirthdayModel): Flow<Response<Unit>>
}

class DeleteBirthdayUseCaseImpl(private val repository: BirthdayRepository) : DeleteBirthdayUseCase {
    override suspend fun invoke(birthday: BirthdayModel): Flow<Response<Unit>> =
        repository.deleteBirthday(birthday)
}