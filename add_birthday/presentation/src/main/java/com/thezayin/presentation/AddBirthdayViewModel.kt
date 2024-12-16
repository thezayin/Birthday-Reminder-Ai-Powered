package com.thezayin.presentation

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
import com.thezayin.presentation.event.BirthdayUiEvent
import com.thezayin.presentation.state.BirthdayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddBirthdayViewModel(
    private val addBirthdayUseCase: AddBirthdayUseCase,
    private val deleteBirthdayUseCase: DeleteBirthdayUseCase,
    private val updateBirthdayUseCase: UpdateBirthdayUseCase,
    private val getAllBirthdaysUseCase: GetAllBirthdaysUseCase,
    private val clearAllBirthdaysUseCase: ClearAllBirthdaysUseCase,
    private val getBirthdayCountUseCase: GetBirthdayCountUseCase,
    private val getBirthdayCountByGroupUseCase: GetBirthdayCountByGroupUseCase,
    private val getBirthdayCountByMonthUseCase: GetBirthdayCountByMonthUseCase,
    private val getBirthdayCountByDayUseCase: GetBirthdayCountByDayUseCase,
    private val getBirthdayCountByYearUseCase: GetBirthdayCountByYearUseCase
)  : ViewModel(){
    private val _uiState = MutableStateFlow(BirthdayUiState())
    val uiState: StateFlow<BirthdayUiState> = _uiState.asStateFlow()


    init {
        fetchAllBirthdays()
    }

    fun handleEvent(event: BirthdayUiEvent) {
        when (event) {
            is BirthdayUiEvent.AddBirthday -> addBirthday(event.birthday)
            is BirthdayUiEvent.DeleteBirthday -> deleteBirthday(event.birthday)
            is BirthdayUiEvent.UpdateBirthday -> updateBirthday(event.birthday)
            is BirthdayUiEvent.FetchAllBirthdays -> fetchAllBirthdays()
            is BirthdayUiEvent.ClearAllBirthdays -> clearAllBirthdays()
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
                        _uiState.update { it.copy(isLoading = false, birthdays = response.data, error = null) }
                    }
                    is Response.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = response.e) }
                    }
                }
            }
        }
    }

    fun addBirthday(birthday: BirthdayModel) {
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

    private fun deleteBirthday(birthday: BirthdayModel) {
        viewModelScope.launch {
            deleteBirthdayUseCase(birthday).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Response.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        fetchAllBirthdays() // Refresh list
                    }
                    is Response.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = response.e) }
                    }
                }
            }
        }
    }

    private fun updateBirthday(birthday: BirthdayModel) {
        viewModelScope.launch {
            updateBirthdayUseCase(birthday).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Response.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        fetchAllBirthdays() // Refresh list
                    }
                    is Response.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = response.e) }
                    }
                }
            }
        }
    }

    private fun clearAllBirthdays() {
        viewModelScope.launch {
            clearAllBirthdaysUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                    is Response.Success -> {
                        _uiState.update { it.copy(isLoading = false, birthdays = emptyList(), error = null) }
                    }
                    is Response.Error -> {
                        _uiState.update { it.copy(isLoading = false, error = response.e) }
                    }
                }
            }
        }
    }
}