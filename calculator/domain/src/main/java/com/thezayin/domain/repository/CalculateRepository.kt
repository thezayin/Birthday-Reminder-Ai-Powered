package com.thezayin.domain.repository

import com.thezayin.domain.model.CalculateModel
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow

interface CalculateRepository {
    fun calculate(birthDate: String, currentDate: String): Flow<Response<CalculateModel>>
}