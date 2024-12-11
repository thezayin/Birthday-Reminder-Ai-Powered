package com.thezayin.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.thezayin.domain.model.CalculateModel
import com.thezayin.domain.repository.CalculateRepository
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class CalculateRepositoryImpl : CalculateRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun calculate(birthDate: String, currentDate: String): Flow<Response<CalculateModel>> =
        flow {
            try {
                emit(Response.Loading)
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val birth = LocalDate.parse(birthDate, formatter)
                val current = LocalDate.parse(currentDate, formatter)
                val age = Period.between(birth, current)
                val result = CalculateModel(
                    age.years.toString(),
                    age.months.toString(),
                    age.days.toString()
                )
                emit(Response.Success(result))
            } catch (e: Exception) {
                emit(Response.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
}