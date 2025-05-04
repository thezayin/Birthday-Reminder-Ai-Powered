package com.thezayin.saved_birthdays

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.domain.usecase.DeleteBirthdayUseCase
import com.thezayin.domain.usecase.GetAllBirthdaysUseCase
import com.thezayin.domain.usecase.UpdateBirthdayUseCase
import com.thezayin.dslrblur.framework.ads.admob.domain.repository.RewardedAdManager
import com.thezayin.framework.ads.admob.domain.repository.InterstitialAdManager
import com.thezayin.framework.remote.RemoteConfig
import com.thezayin.framework.utils.Response
import com.thezayin.saved_birthdays.events.SavedBirthdayUiEvent
import com.thezayin.saved_birthdays.state.SavedBirthdayUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedBirthdaysViewModel(
    private val getAllBirthdaysUseCase: GetAllBirthdaysUseCase,
    private val deleteBirthdayUseCase: DeleteBirthdayUseCase,
    private val updateBirthdayUseCase: UpdateBirthdayUseCase,
    val remoteConfig: RemoteConfig,
    val rewardedAdManager: RewardedAdManager,
    val interstitialAdManager: InterstitialAdManager,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SavedBirthdayUiState())
    val uiState: StateFlow<SavedBirthdayUiState> = _uiState.asStateFlow()

    init {
        fetchAllBirthdays()
    }

    private fun handleEvent(event: SavedBirthdayUiEvent) {
        when (event) {
            is SavedBirthdayUiEvent.ShowLoading -> _uiState.update { it.copy(isLoading = event.loading) }
            is SavedBirthdayUiEvent.ErrorMessage -> _uiState.update { it.copy(errorMessage = event.message) }
            is SavedBirthdayUiEvent.DeleteSuccess -> _uiState.update { it.copy(deleteSuccess = true) }
            is SavedBirthdayUiEvent.ShowError -> _uiState.update { it.copy(isError = event.error) }
            is SavedBirthdayUiEvent.UpdateSuccess -> _uiState.update { it.copy(updateSuccess = true) }
            is SavedBirthdayUiEvent.BirthdayList -> _uiState.update { it.copy(birthdays = event.birthdays) }
            is SavedBirthdayUiEvent.FilterBirthdays -> _uiState.update { it.copy(filteredBirthdays = event.filterList) }
        }
    }

    // Fetch all birthdays
    private fun fetchAllBirthdays() {
        viewModelScope.launch {
            getAllBirthdaysUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> setLoader(true)
                    is Response.Success -> {
                        setBirthdays(response.data)
                        setFilterList(response.data)
                        setLoader(false)
                    }

                    is Response.Error -> {
                        setLoader(false)
                        setError(true)
                        setErrorMessage(response.e)
                    }
                }
            }
        }
    }

    fun setGroupFilter(group: String) {
        _uiState.update { it.copy(selectedGroup = group) }
        val filteredList = if (group == "All") {
            _uiState.value.birthdays
        } else {
            _uiState.value.birthdays.filter { it.group == group }
        }
        setFilterList(filteredList)
    }

    // Delete a birthday
    fun deleteBirthday(birthday: BirthdayModel) {
        viewModelScope.launch {
            deleteBirthdayUseCase(birthday).collect { response ->
                when (response) {
                    is Response.Loading -> setLoader(true)
                    is Response.Success -> {
                        fetchAllBirthdays() // Refresh list after deletion
                        setDeleteSuccess()
                        setLoader(false)
                    }

                    is Response.Error -> {
                        setLoader(false)
                        setError(true)
                        setErrorMessage(response.e)
                    }
                }
            }
        }
    }

    // Update a birthday
    fun updateBirthday(birthday: BirthdayModel) {
        viewModelScope.launch {
            updateBirthdayUseCase(birthday).collect { response ->
                when (response) {
                    is Response.Loading -> setLoader(true)
                    is Response.Success -> {
                        fetchAllBirthdays() // Refresh list after update
                        setUpdateSuccess()
                        setLoader(false)
                    }

                    is Response.Error -> {
                        setLoader(false)
                        setError(true)
                        setErrorMessage(response.e)
                    }
                }
            }
        }
    }

    private fun setLoader(isLoading: Boolean) {
        handleEvent(SavedBirthdayUiEvent.ShowLoading(isLoading))
    }

    fun setError(isError: Boolean) {
        handleEvent(SavedBirthdayUiEvent.ShowError(isError))
    }

    private fun setErrorMessage(message: String) {
        handleEvent(SavedBirthdayUiEvent.ErrorMessage(message))
    }

    private fun setDeleteSuccess() {
        handleEvent(SavedBirthdayUiEvent.DeleteSuccess())
    }

    private fun setUpdateSuccess() {
        handleEvent(SavedBirthdayUiEvent.UpdateSuccess())
    }

    private fun setBirthdays(birthdays: List<BirthdayModel>) {
        handleEvent(SavedBirthdayUiEvent.BirthdayList(birthdays))
    }

    private fun setFilterList(filterList: List<BirthdayModel>) {
        handleEvent(SavedBirthdayUiEvent.FilterBirthdays(filterList))
    }
}