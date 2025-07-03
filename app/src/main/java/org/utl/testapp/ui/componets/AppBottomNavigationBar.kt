package org.utl.testapp.ui.componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.utl.testapp.Screen
import org.utl.testapp.ui.theme.Dark_Primary
import org.utl.testapp.ui.theme.Light_Primary

@Composable
fun AppBottomNavigationBar(
    selectedScreen: Screen,
    onScreenSelected: (Screen) -> Unit,
    currentDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    showThemeToggle: Boolean = true // Nuevo parámetro para activar/desactivar el botón de tema
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = if (currentDarkTheme) Dark_Primary else Light_Primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        // Botón para cambiar tema (visible solo si showThemeToggle es true)
        if (showThemeToggle) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (currentDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                        contentDescription = if (currentDarkTheme) "Cambiar a tema claro" else "Cambiar a tema oscuro"
                    )
                },
                label = {
                    Text(
                        text = if (currentDarkTheme) "Claro" else "Oscuro",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                selected = false,
                onClick = onToggleTheme
            )
        }
        // Configuración
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Configuración") },
            label = {
                Text(
                    text = "Configuración",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            selected = selectedScreen == Screen.SETTINGS,
            onClick = { onScreenSelected(Screen.SETTINGS) }
        )
        // Medidor PH (Home)
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Medidor PH") },
            label = {
                Text(
                    text = "Medidor PH",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            selected = selectedScreen == Screen.PH_METER,
            onClick = { onScreenSelected(Screen.PH_METER) }
        )
        // Análisis
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Analytics, contentDescription = "Análisis") },
            label = {
                Text(
                    text = "Análisis",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            selected = selectedScreen == Screen.ANALYTICS,
            onClick = { onScreenSelected(Screen.ANALYTICS) }
        )
    }
}