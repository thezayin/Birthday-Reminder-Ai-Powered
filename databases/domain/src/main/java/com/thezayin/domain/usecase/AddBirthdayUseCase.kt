package com.thezayin.domain.usecase

import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.repository.BirthdayRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface AddBirthdayUseCase : suspend (BirthdayModel) -> Flow<Response<Unit>>

class AddBirthdayUseCaseImpl(private val repository: BirthdayRepository) : AddBirthdayUseCase {
    override suspend fun invoke(birthday: BirthdayModel): Flow<Response<Unit>> =
        repository.addBirthday(birthday)
}