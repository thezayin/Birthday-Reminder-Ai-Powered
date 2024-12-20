package com.thezayin.datemate.navigation.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.thezayin.add_birthday.AddBirthdayScreen
import com.thezayin.components.SmsPermissionHandler

@Composable
fun AddBirthdayScreenWithPermissions(
    navigateBack: () -> Unit
) {
    var permissionGranted by remember { mutableStateOf(false) }

    SmsPermissionHandler(
        onPermissionGranted = {
            permissionGranted = true
        }
    )

    if (permissionGranted) {
        AddBirthdayScreen(
            navigateBack = navigateBack
        )
    }
}