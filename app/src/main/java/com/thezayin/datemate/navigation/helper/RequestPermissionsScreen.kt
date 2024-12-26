// RequestPermissionsScreen.kt
package com.thezayin.datemate.navigation.helper

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun RequestPermissionsScreen(
    onPermissionsGranted: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Define all required permissions
    val requiredPermissions = listOf(
        Permissions.SEND_SMS,
        Permissions.POST_NOTIFICATIONS
    )

    // Initialize permission states
    var permissionStates by remember {
        mutableStateOf(
            requiredPermissions.associateWith { PermissionState.NotRequested }
        )
    }

    // Launcher to request multiple permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            // Update permission states based on results
            val updatedStates = permissionStates.toMutableMap()
            permissions.forEach { (permission, isGranted) ->
                updatedStates[permission] = if (isGranted) {
                    PermissionState.Granted
                } else {
                    val activity = context as? ComponentActivity
                    val shouldShowRationale = activity?.shouldShowRequestPermissionRationale(permission) ?: false
                    if (shouldShowRationale) {
                        PermissionState.Denied
                    } else {
                        PermissionState.PermanentlyDenied
                    }
                }
            }
            permissionStates = updatedStates
        }
    )

    // Function to check if all regular permissions are granted
    fun allRegularPermissionsGranted(): Boolean {
        return permissionStates.values.all { it == PermissionState.Granted }
    }

    // Function to get list of denied regular permissions
    fun getDeniedPermissions(): List<String> {
        return permissionStates.filter { it.value == PermissionState.Denied }.keys.toList()
    }

    // Function to get list of permanently denied regular permissions
    fun getPermanentlyDeniedPermissions(): List<String> {
        return permissionStates.filter { it.value == PermissionState.PermanentlyDenied }.keys.toList()
    }

    // Function to request regular permissions
    fun requestRegularPermissions() {
        permissionLauncher.launch(requiredPermissions.toTypedArray())
    }

    // Function to handle SCHEDULE_EXACT_ALARM permission
    var alarmPermissionState by remember { mutableStateOf(PermissionState.NotRequested) }

    // Check and request SCHEDULE_EXACT_ALARM based on Android version
    val isAndroid12OrAbove = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S

    // Function to check if SCHEDULE_EXACT_ALARM is granted
    fun isExactAlarmPermissionGranted(): Boolean {
        return if (isAndroid12OrAbove) {
            val alarmManager = context.getSystemService(android.app.AlarmManager::class.java)
            alarmManager?.canScheduleExactAlarms() ?: false
        } else {
            true // Not needed below Android 12
        }
    }

    // Observe lifecycle to recheck permissions when app resumes
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // Recheck regular permissions
                val updatedRegularStates = permissionStates.toMutableMap()
                requiredPermissions.forEach { permission ->
                    val isGranted = ContextCompat.checkSelfPermission(
                        context,
                        permission
                    ) == PackageManager.PERMISSION_GRANTED
                    if (isGranted) {
                        updatedRegularStates[permission] = PermissionState.Granted
                    } else {
                        val activity = context as? ComponentActivity
                        val shouldShowRationale = activity?.shouldShowRequestPermissionRationale(permission) ?: false
                        updatedRegularStates[permission] = if (shouldShowRationale) {
                            PermissionState.Denied
                        } else {
                            PermissionState.PermanentlyDenied
                        }
                    }
                }
                permissionStates = updatedRegularStates

                // Recheck SCHEDULE_EXACT_ALARM permission
                if (isAndroid12OrAbove) {
                    val isAlarmGranted = isExactAlarmPermissionGranted()
                    alarmPermissionState = if (isAlarmGranted) {
                        PermissionState.Granted
                    } else {
                        PermissionState.PermanentlyDenied // Since there's no direct prompt
                    }
                }

                // If all permissions are granted, proceed
                if (allRegularPermissionsGranted() && (!isAndroid12OrAbove || alarmPermissionState == PermissionState.Granted)) {
                    onPermissionsGranted()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Initial permission checks and requests
    LaunchedEffect(Unit) {
        // Check regular permissions
        val updatedRegularStates = permissionStates.toMutableMap()
        var needsToRequestRegular = false
        requiredPermissions.forEach { permission ->
            val isGranted = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
            if (isGranted) {
                updatedRegularStates[permission] = PermissionState.Granted
            } else {
                needsToRequestRegular = true
                val activity = context as? ComponentActivity
                val shouldShowRationale = activity?.shouldShowRequestPermissionRationale(permission) ?: false
                updatedRegularStates[permission] = if (shouldShowRationale) {
                    PermissionState.Denied
                } else {
                    PermissionState.PermanentlyDenied
                }
            }
        }
        permissionStates = updatedRegularStates

        // Check SCHEDULE_EXACT_ALARM permission
        if (isAndroid12OrAbove) {
            alarmPermissionState = if (isExactAlarmPermissionGranted()) {
                PermissionState.Granted
            } else {
                // No direct prompt; user must enable via settings
                PermissionState.PermanentlyDenied
            }
        } else {
            alarmPermissionState = PermissionState.Granted
        }

        // If regular permissions are not all granted, request them
        if (!allRegularPermissionsGranted()) {
            requestRegularPermissions()
        }

        // If SCHEDULE_EXACT_ALARM is not granted, handle accordingly
        if (isAndroid12OrAbove && alarmPermissionState != PermissionState.Granted) {
            // Guide the user to settings
            // This will be handled in the UI below
        }

        // If all permissions are already granted, proceed
        if (allRegularPermissionsGranted() && (!isAndroid12OrAbove || alarmPermissionState == PermissionState.Granted)) {
            onPermissionsGranted()
        }
    }

    // UI based on permission states
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            // All permissions granted
            allRegularPermissionsGranted() && (!isAndroid12OrAbove || alarmPermissionState == PermissionState.Granted) -> {
                // Navigate to home screen
                LaunchedEffect(Unit) {
                    onPermissionsGranted()
                }
            }

            // SCHEDULE_EXACT_ALARM is permanently denied
            isAndroid12OrAbove && alarmPermissionState == PermissionState.PermanentlyDenied -> {
                PermanentlyDeniedAlarmPermissionUI()
            }

            // Any regular permission is permanently denied
            getPermanentlyDeniedPermissions().isNotEmpty() -> {
                PermanentlyDeniedRegularPermissionsUI(
                    permanentlyDeniedPermissions = getPermanentlyDeniedPermissions()
                )
            }

            // Any regular permission is denied
            getDeniedPermissions().isNotEmpty() -> {
                DeniedRegularPermissionsUI(
                    deniedPermissions = getDeniedPermissions(),
                    onGrantClick = { requestRegularPermissions() }
                )
            }

            // All regular permissions are granted but SCHEDULE_EXACT_ALARM is not
            allRegularPermissionsGranted() && isAndroid12OrAbove && alarmPermissionState != PermissionState.Granted -> {
                PermanentlyDeniedAlarmPermissionUI()
            }

            else -> {
                // Default UI (can be loading or request state)
                Text(text = "Requesting permissions...")
            }
        }
    }
}


@Composable
fun DeniedRegularPermissionsUI(
    deniedPermissions: List<String>,
    onGrantClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "The app requires the following permissions to function properly:",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        deniedPermissions.forEach { permission ->
            Text(text = "- ${getPermissionFriendlyName(permission)}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onGrantClick) {
            Text("Grant Permissions")
        }
    }
}

@Composable
fun PermanentlyDeniedRegularPermissionsUI(
    permanentlyDeniedPermissions: List<String>
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "The following permissions are permanently denied. Please enable them in settings:",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        permanentlyDeniedPermissions.forEach { permission ->
            Text(text = "- ${getPermissionFriendlyName(permission)}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Open app settings
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }) {
            Text("Open Settings")
        }
    }
}

@Composable
fun PermanentlyDeniedAlarmPermissionUI() {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Scheduling exact alarms is essential for the app. Please enable it in settings:",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            // Open app settings
            val intent = Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                Uri.fromParts("package", context.packageName, null)
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }) {
            Text("Open Settings")
        }
    }
}

@Composable
fun DefaultPermissionRequestUI(
    onGrantClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "The app requires certain permissions to function.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onGrantClick) {
            Text("Request Permissions")
        }
    }
}

// Utility function to get a user-friendly name for permissions
fun getPermissionFriendlyName(permission: String): String {
    return when (permission) {
        Permissions.SEND_SMS -> "Send SMS"
        Permissions.POST_NOTIFICATIONS -> "Post Notifications"
        else -> permission
    }
}
