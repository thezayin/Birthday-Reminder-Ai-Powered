package com.thezayin.add_birthday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.add_birthday.event.BirthdayUiEvent
import com.thezayin.add_birthday.state.BirthdayUiState
import com.thezayin.analytics.analytics.Analytics
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.usecase.AddBirthdayUseCase
import com.thezayin.domain.usecase.GetAllBirthdaysUseCase
import com.thezayin.dslrblur.framework.ads.admob.domain.repository.RewardedAdManager
import com.thezayin.events.AnalyticsEvent
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddBirthdayViewModel(
    val analytics: Analytics,
    val remoteConfig: RemoteConfig,
    private val addBirthdayUseCase: AddBirthdayUseCase,
    private val getAllBirthdaysUseCase: GetAllBirthdaysUseCase,
    internal val rewardedManager: RewardedAdManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(BirthdayUiState())
    val uiState: StateFlow<BirthdayUiState> = _uiState.asStateFlow()

    private var forceSave = false // Flag to bypass duplicate check


    init {
        analytics.logEvent(AnalyticsEvent.AddBirthdayStarted())
        fetchAllBirthdays()
    }

    private fun handleEvent(event: BirthdayUiEvent) {
        when (event) {
            is BirthdayUiEvent.FetchAllBirthdays -> _uiState.update { it.copy(birthdays = event.birthdays) }
            is BirthdayUiEvent.ErrorMessage -> _uiState.update { it.copy(error = event.error) }
            is BirthdayUiEvent.IsError -> _uiState.update { it.copy(isError = event.error) }
            is BirthdayUiEvent.IsLoading -> _uiState.update { it.copy(isLoading = event.loading) }
            is BirthdayUiEvent.IsAdded -> _uiState.update { it.copy(isAdded = event.added) }
            is BirthdayUiEvent.DuplicateBirthday -> _uiState.update { it.copy(isDuplicate = event.duplicate) }
        }
    }

    private fun fetchAllBirthdays() {
        viewModelScope.launch {
            getAllBirthdaysUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> isLoading(true)
                    is Response.Success -> {
                        setBirthdays(response.data)
                        isLoading(false)
                    }

                    is Response.Error -> {
                        isLoading(false)
                        isError(true)
                        errorMessage(response.e)
                        analytics.logEvent(AnalyticsEvent.AddBirthdayFailure(errorMessage = response.e))
                    }
                }
            }
        }
    }

    fun addBirthday(birthday: BirthdayModel) {
        viewModelScope.launch {
            val isDuplicate = !forceSave && uiState.value.birthdays.any { existingBirthday ->
                existingBirthday.name == birthday.name &&
                        existingBirthday.day == birthday.day &&
                        existingBirthday.month == birthday.month &&
                        existingBirthday.notifyAt == birthday.notifyAt
            }

            if (isDuplicate) {
                isDuplicate(true)
                analytics.logEvent(AnalyticsEvent.AddBirthdayDuplicateDetected())
            } else {
                addBirthdayToDatabase(birthday)
            }
        }
    }

    fun confirmAddDuplicate(birthday: BirthdayModel) {
        forceSave = true
        addBirthday(birthday)
        forceSave = false
        analytics.logEvent(AnalyticsEvent.AddBirthdayDuplicateConfirmed())
    }

    private fun addBirthdayToDatabase(birthday: BirthdayModel) {
        viewModelScope.launch {
            addBirthdayUseCase(birthday).collect { response ->
                when (response) {
                    is Response.Loading -> isLoading(true)
                    is Response.Success -> {
                        isAdded(true)
                        isLoading(false)
                        analytics.logEvent(AnalyticsEvent.AddBirthdaySuccess())
                    }

                    is Response.Error -> {
                        isLoading(false)
                        isError(true)
                        errorMessage(response.e)
                        analytics.logEvent(AnalyticsEvent.AddBirthdayFailure(errorMessage = response.e))
                    }
                }
            }
        }
    }

    fun isDuplicate(isDuplicate: Boolean) {
        handleEvent(BirthdayUiEvent.DuplicateBirthday(isDuplicate))
        if (!isDuplicate) {
            analytics.logEvent(AnalyticsEvent.AddBirthdayDuplicateCancelled())
        }
    }

    private fun setBirthdays(birthday: List<BirthdayModel>) {
        handleEvent(BirthdayUiEvent.FetchAllBirthdays(birthday))
    }

    fun isLoading(isLoading: Boolean) {
        handleEvent(BirthdayUiEvent.IsLoading(isLoading))
    }

    fun isError(isError: Boolean) {
        handleEvent(BirthdayUiEvent.IsError(isError))
    }

    fun isAdded(isAdded: Boolean) {
        handleEvent(BirthdayUiEvent.IsAdded(isAdded))
    }

    private fun errorMessage(error: String) {
        handleEvent(BirthdayUiEvent.ErrorMessage(error))
        analytics.logEvent(AnalyticsEvent.AddBirthdayFailure(errorMessage = error))
    }
}
