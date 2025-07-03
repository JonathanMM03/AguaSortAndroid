package org.utl.testapp.ui.componets

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestPermissions(activity: Activity, onGranted: () -> Unit = {}) {
    var showDialog by remember { mutableStateOf(false) }

    val permission = Manifest.permission.POST_NOTIFICATIONS

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGranted()
        } else {
            showDialog = true
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(permission)
        } else {
            onGranted()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Permiso de notificaciones") },
            text = {
                Text("Esta app necesita permiso para enviarte notificaciones importantes.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    requestPermissionLauncher.launch(permission)
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    activity.finishAffinity()
                }) {
                    Text("Cerrar")
                }
            }
        )
    }
}