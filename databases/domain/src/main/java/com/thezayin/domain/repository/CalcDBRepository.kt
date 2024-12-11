package com.thezayin.domain.repository

import com.thezayin.domain.model.AgeCalModel
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface CalcDBRepository {
    fun getAllCalHistory(): Flow<Response<List<AgeCalModel>>>
    fun insertCalHistory(ageCalModel: AgeCalModel): Flow<Response<Boolean>>
    fun clearCalHistory(): Flow<Response<Boolean>>
}