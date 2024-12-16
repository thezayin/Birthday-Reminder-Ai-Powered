package com.thezayin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.ads.GoogleManager
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.usecase.AddBirthdayUseCase
import com.thezayin.domain.usecase.GetAllBirthdaysUseCase
import com.thezayin.event.BirthdayUiEvent
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.state.BirthdayUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddBirthdayViewModel(
    private val googleManager: GoogleManager,
    val remoteConfig: RemoteConfig,
    private val addBirthdayUseCase: AddBirthdayUseCase,
    private val getAllBirthdaysUseCase: GetAllBirthdaysUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(BirthdayUiState())
    val uiState: StateFlow<BirthdayUiState> = _uiState.asStateFlow()

    var nativeAd = mutableStateOf<NativeAd?>(null)
        private set

    private var forceSave = false // Flag to bypass duplicate check

    fun getNativeAd() = viewModelScope.launch {
        nativeAd.value = googleManager.createNativeAd().apply {} ?: run {
            delay(3000)
            googleManager.createNativeAd()
        }
    }

    init {
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
                isDuplicate(true) // Trigger duplicate dialog
            } else {
                addBirthdayToDatabase(birthday)
            }
        }
    }

    fun confirmAddDuplicate(birthday: BirthdayModel) {
        forceSave = true // Allow bypassing duplicate check
        addBirthday(birthday)
        forceSave = false // Reset flag after saving
    }

    private fun addBirthdayToDatabase(birthday: BirthdayModel) {
        viewModelScope.launch {
            addBirthdayUseCase(birthday).collect { response ->
                when (response) {
                    is Response.Loading -> isLoading(true)
                    is Response.Success -> {
                        isAdded(true)
                        isLoading(false)
                    }

                    is Response.Error -> {
                        isLoading(false)
                        isError(true)
                        errorMessage(response.e)
                    }
                }
            }
        }
    }

    fun isDuplicate(isDuplicate: Boolean) {
        handleEvent(BirthdayUiEvent.DuplicateBirthday(isDuplicate))
    }

    private fun setBirthdays(birthday: List<BirthdayModel>) {
        handleEvent(BirthdayUiEvent.FetchAllBirthdays(birthday))
    }

    private fun isLoading(isLoading: Boolean) {
        handleEvent(BirthdayUiEvent.IsLoading(isLoading))
    }

    fun isError(isError: Boolean) {
        handleEvent(BirthdayUiEvent.IsError(isError))
    }

    private fun isAdded(isAdded: Boolean) {
        handleEvent(BirthdayUiEvent.IsAdded(isAdded))
    }

    private fun errorMessage(error: String) {
        handleEvent(BirthdayUiEvent.ErrorMessage(error))
    }
}
