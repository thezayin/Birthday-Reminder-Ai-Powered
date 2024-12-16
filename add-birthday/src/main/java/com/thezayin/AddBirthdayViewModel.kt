package com.thezayin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.usecase.AddBirthdayUseCase
import com.thezayin.domain.usecase.ClearAllBirthdaysUseCase
import com.thezayin.domain.usecase.DeleteBirthdayUseCase
import com.thezayin.domain.usecase.GetAllBirthdaysUseCase
import com.thezayin.domain.usecase.GetBirthdayCountByDayUseCase
import com.thezayin.domain.usecase.GetBirthdayCountByGroupUseCase
import com.thezayin.domain.usecase.GetBirthdayCountByMonthUseCase
import com.thezayin.domain.usecase.GetBirthdayCountByYearUseCase
import com.thezayin.domain.usecase.GetBirthdayCountUseCase
import com.thezayin.domain.usecase.UpdateBirthdayUseCase
import com.thezayin.framework.utils.Response
import com.thezayin.event.BirthdayUiEvent
import com.thezayin.state.BirthdayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddBirthdayViewModel(
    private val addBirthdayUseCase: AddBirthdayUseCase,
    private val getAllBirthdaysUseCase: GetAllBirthdaysUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(BirthdayUiState())
    val uiState: StateFlow<BirthdayUiState> = _uiState.asStateFlow()


    init {
        fetchAllBirthdays()
    }

    fun handleEvent(event: BirthdayUiEvent) {
        when (event) {
            is BirthdayUiEvent.AddBirthday -> addBirthday(event.birthday)
            is BirthdayUiEvent.FetchAllBirthdays -> fetchAllBirthdays()
        }
    }

    private fun fetchAllBirthdays() {
        viewModelScope.launch {
            getAllBirthdaysUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }

                    is Response.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                birthdays = response.data,
                                error = null
                            )
                        }
                    }

                    is Response.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = response.e) }
                    }
                }
            }
        }
    }

    private fun addBirthday(birthday: BirthdayModel) {
        viewModelScope.launch {
            addBirthdayUseCase(birthday).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Response.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        fetchAllBirthdays() // Refresh list
                        // TODO: Schedule notification here
                    }

                    is Response.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = response.e) }
                    }
                }
            }
        }
    }
}