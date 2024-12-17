package com.thezayin.saved_birthdays

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.thezayin.saved_birthdays.components.SavedBirthdayContent
import org.koin.compose.koinInject

@Composable
fun SavedBirthdaysScreen(
    navigateBack: () -> Unit
) {
    val viewModel: SavedBirthdaysViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()
    val activity = LocalContext.current as Activity

    val nativeAd = remember { viewModel.nativeAd }
    val remoteConfig = viewModel.remoteConfig.adConfigs

    // Remember states for input fields
    val name = remember { mutableStateOf(TextFieldValue()) }
    val day = remember { mutableStateOf(TextFieldValue()) }
    val month = remember { mutableStateOf(TextFieldValue()) }
    val year = remember { mutableStateOf(TextFieldValue()) } // Optional Year

    val isButtonEnabled = remember { mutableStateOf(false) }

    // Notification Time Fields
    val notifyHour = remember { mutableStateOf(TextFieldValue("12")) } // Default to 12
    val notifyMinute = remember { mutableStateOf(TextFieldValue("00")) } // Default to 00
    val notifyPeriod = remember { mutableStateOf("AM") } // Default to AM

    val groups = listOf("Family", "Friends", "Work", "Teacher", "Other") // Example groups

    val error = uiState.isError
    val selectedGroup = uiState.selectedGroup
    val isDeleted = uiState.deleteSuccess
    val errorMessage = uiState.errorMessage
    val isError = uiState.isError
    val isLoading = uiState.isLoading
    val isUpdated = uiState.updateSuccess
    val filteredBirthdays = uiState.filteredBirthdays
    val showBottomAd = remoteConfig.nativeAdOnHomeScreen
    val showLoadingAd = remoteConfig.nativeAdOnResultLoadingDialog

    SavedBirthdayContent(
        birthdays = uiState.birthdays,
        groups = groups,
        selectedGroup = selectedGroup,
        onDelete = { birthday -> viewModel.deleteBirthday(birthday) },
        onUpdate = { birthday -> viewModel.updateBirthday(birthday) },
        onBackClick = navigateBack,
        error = errorMessage,
        isLoading = isLoading,
        showError = isError,
        nativeAd = nativeAd.value,
        showBottomAd = showBottomAd,
        showLoadingAd = showLoadingAd,
        coroutineScope = viewModel.viewModelScope,
        navigateBack = navigateBack,
        filteredBirthdays = filteredBirthdays,
        dismissErrorDialog = { viewModel.setError(isError = false) },
        fetchNativeAd = { viewModel.getNativeAd() },
        onAddBirthdayClick = { navigateBack() },
        onSelectedGroup = { group -> viewModel.setGroupFilter(group) }
    )
}