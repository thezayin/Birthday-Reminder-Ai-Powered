package com.thezayin.saved_birthdays.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import com.thezayin.components.ErrorQueryDialog
import com.thezayin.components.LoadingDialog
import com.thezayin.domain.model.BirthdayModel
import com.thezayin.saved_birthdays.utils.getMonthName
import com.thezayin.values.R
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

@Composable
fun SavedBirthdayContent(
    onDelete: (BirthdayModel) -> Unit,
    onUpdate: (BirthdayModel) -> Unit, // Callback for editing
    selectedGroup: String,
    filteredBirthdays: List<BirthdayModel>,
    error: String?,
    isLoading: Boolean,
    showError: Boolean,
    navigateBack: () -> Unit,
    dismissErrorDialog: () -> Unit,
    onSelectedGroup: (String) -> Unit,
) {
    var selectedBirthday by remember { mutableStateOf<BirthdayModel?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }


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
        LoadingDialog()
    }

    // Main layout with Scaffold
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorResource(id = R.color.background),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 25.sdp, start = 15.sdp, end = 20.sdp
                    ),  // Padding for top and horizontal space
                horizontalArrangement = Arrangement.Start,  // Space elements across the row
                verticalAlignment = Alignment.CenterVertically  // Align items vertically in the center
            ) {
                // Back Button (represented as an icon image)
                Image(painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back Button",  // Added content description for accessibility
                    modifier = Modifier
                        .size(18.sdp)
                        .clickable { navigateBack() }  // Trigger the back click event
                )
            }
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