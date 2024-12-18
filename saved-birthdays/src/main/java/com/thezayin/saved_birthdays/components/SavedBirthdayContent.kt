package com.thezayin.saved_birthdays.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.nativead.NativeAd
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.framework.lifecycles.ComposableLifecycle
import com.thezayin.framework.nativead.GoogleNativeAd
import com.thezayin.framework.nativead.GoogleNativeAdStyle
import com.thezayin.saved_birthdays.utils.getMonthName
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
fun SavedBirthdayContent(
    birthdays: List<BirthdayModel>,
    onDelete: (BirthdayModel) -> Unit,
    onUpdate: (BirthdayModel) -> Unit, // Callback for editing
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedGroup: String,
    filteredBirthdays: List<BirthdayModel>,
    groups: List<String>,
    error: String?,
    isLoading: Boolean,
    showError: Boolean,
    nativeAd: NativeAd?,
    showBottomAd: Boolean,
    showLoadingAd: Boolean,
    coroutineScope: CoroutineScope,
    navigateBack: () -> Unit,
    dismissErrorDialog: () -> Unit,
    fetchNativeAd: () -> Unit,
    onAddBirthdayClick: () -> Unit,
    onSelectedGroup: (String) -> Unit,
) {
    var selectedBirthday by remember { mutableStateOf<BirthdayModel?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }


    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_START -> {
                coroutineScope.launch {
                    while (isActive) {
                        fetchNativeAd()
                        delay(20000L) // Fetch a new ad every 20 seconds
                    }
                }
            }

            else -> Unit // No action needed for other lifecycle events
        }
    }

    // Show error dialog if there is an error
    if (showError) {
        ErrorQueryDialog(
            showDialog = { dismissErrorDialog() },
            callback = {},
            error = error ?: "unknown error"
        )
    }


    // Display loading dialog with optional native ad
    if (isLoading) {
        LoadingDialog(ad = {
            GoogleNativeAd(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                nativeAd = nativeAd,
                style = GoogleNativeAdStyle.Small
            )
        }, nativeAd = { fetchNativeAd() }, showAd = showLoadingAd
        )
    }


    // Grouping birthdays by selected group or showing all
    val groupedBirthdays = birthdays.groupBy { it.month } // Group by month

    // Main layout with Scaffold
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            // Top Bar
            SavedBirthdaysTopBar(
                title = "Saved Birthdays",
                onBackClick = onBackClick
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 10.sdp, vertical = 8.sdp)
        ) {
            GroupFilter(
                options = listOf("All", "Family", "Friends", "Work", "Other"),
                selectedOption = selectedGroup,
                onOptionSelected = { group -> onSelectedGroup(group) }
            )

            // Birthday List with Swipe-to-Delete
            // Show message if no birthdays are found
            if (filteredBirthdays.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No birthdays for this group",
                        color = colorResource(R.color.text_color),
                        fontSize = 18.ssp,
                        fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            } else {
                // Birthday List
                BirthdayListWithSwipe(
                    birthdays = filteredBirthdays.groupBy { getMonthName(it.month) },
                    onDelete = { birthday -> onDelete(birthday) },
                    onCardClick = { birthday ->
                        selectedBirthday = birthday
                        isBottomSheetVisible = true
                    }
                )
            }
            // Bottom Sheet
            if (isBottomSheetVisible && selectedBirthday != null) {
                EditBirthdayDetailsBottomSheet(
                    birthday = selectedBirthday!!,
                    onDismiss = { isBottomSheetVisible = false },
                    onDelete = {
                        onDelete(selectedBirthday!!)
                        isBottomSheetVisible = false
                    },
                    onSave = { updatedBirthday ->
                        onUpdate(updatedBirthday)
                        isBottomSheetVisible = false
                    }
                )
            }
        }
    }
}