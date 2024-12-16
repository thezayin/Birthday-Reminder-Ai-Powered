package com.thezayin.domain.usecase

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface UpdateBirthdayUseCase : suspend (BirthdayModel) -> Flow<Response<Unit>>

class UpdateBirthdayUseCaseImpl(private val repository: BirthdayRepository) :
    UpdateBirthdayUseCase {
    override suspend fun invoke(birthday: BirthdayModel): Flow<Response<Unit>> =
        repository.updateBirthday(birthday)
}
