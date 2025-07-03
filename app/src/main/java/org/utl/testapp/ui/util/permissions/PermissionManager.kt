package org.utl.testapp.ui.util.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

/**
 * Clase de utilidad para gestionar la verificación y solicitud de permisos en la aplicación.
 * Centraliza la lógica para permisos como POST_NOTIFICATIONS y VIBRATE.
 */
class PermissionManager(private val context: Context) {

    /**
     * Verifica si el permiso de vibración está concedido.
     * El permiso de vibración es un permiso "normal" y se concede automáticamente al instalar.
     *
     * @return true si el permiso de vibración está concedido, false en caso contrario.
     */
    fun hasVibratePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.VIBRATE
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Verifica si el permiso de notificaciones está concedido.
     * Para Android 13 (API 33) y superiores, este es un permiso de tiempo de ejecución.
     * Para versiones anteriores, se concede automáticamente al instalar la app.
     *
     * @return true si el permiso de notificaciones está concedido, false en caso contrario.
     */
    fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Permiso concedido automáticamente en versiones anteriores a Android 13
            true
        }
    }

    /**
     * Solicita el permiso POST_NOTIFICATIONS si es necesario (para Android 13+).
     * Esta función debe ser llamada desde un componente de UI que pueda lanzar un ActivityResultLauncher.
     *
     * @param requestLauncher El [ActivityResultLauncher] para iniciar la solicitud de permiso.
     * @param onPermissionGranted Callback que se invoca si el permiso ya está concedido o si se concede ahora.
     * @param onPermissionDenied Callback que se invoca si el permiso es denegado por el usuario.
     * @param shouldShowRationale Callback para indicar a la UI que debe mostrar una explicación antes de pedir el permiso.
     * Retorna true si se debe mostrar la explicación.
     */
    fun requestNotificationPermission(
        requestLauncher: ActivityResultLauncher<String>,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit,
        shouldShowRationale: () -> Boolean // Esta parte es clave para la UI
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                hasNotificationPermission() -> {
                    // Permiso ya concedido
                    onPermissionGranted()
                }
                shouldShowRationale() -> {
                }
                else -> {
                    // Pide el permiso directamente
                    requestLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Versiones de Android anteriores a 13: permiso concedido por defecto.
            onPermissionGranted()
        }
    }
}