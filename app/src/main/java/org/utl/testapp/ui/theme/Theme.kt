package org.utl.testapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light Theme
private val LightColorScheme = lightColorScheme(
    primary = Light_Primary,
    onPrimary = Light_OnPrimary,
    primaryContainer = Light_PrimaryContainer,
    onPrimaryContainer = Light_OnPrimaryContainer,
    secondary = Light_Secondary,
    onSecondary = Light_OnSecondary,
    secondaryContainer = Light_SecondaryContainer,
    onSecondaryContainer = Light_OnSecondaryContainer,
    tertiary = Light_Tertiary,
    onTertiary = Light_OnTertiary,
    tertiaryContainer = Light_TertiaryContainer,
    onTertiaryContainer = Light_OnTertiaryContainer,
    error = Light_Error,
    onError = Light_OnError,
    errorContainer = Light_Error,
    onErrorContainer = Light_OnError,
    background = Light_Background,
    onBackground = Light_OnBackground,
    surface = Light_Surface,
    onSurface = Light_OnSurface,
    surfaceVariant = Light_SurfaceVariant,
    onSurfaceVariant = Light_OnSurfaceVariant,
    outline = Light_Outline,
    inverseOnSurface = Color.White,
    inverseSurface = Color.Black,
    inversePrimary = Dark_Primary,
    surfaceTint = Light_Primary,
    outlineVariant = Light_Outline,
    scrim = Color.Black,
)

// Dark Theme
private val DarkColorScheme = darkColorScheme(
    primary = Dark_Primary,
    onPrimary = Dark_OnPrimary,
    primaryContainer = Dark_PrimaryContainer,
    onPrimaryContainer = Dark_OnPrimaryContainer,
    secondary = Dark_Secondary,
    onSecondary = Dark_OnSecondary,
    secondaryContainer = Dark_SecondaryContainer,
    onSecondaryContainer = Dark_OnSecondaryContainer,
    tertiary = Dark_Tertiary,
    onTertiary = Dark_OnTertiary,
    tertiaryContainer = Dark_TertiaryContainer,
    onTertiaryContainer = Dark_OnTertiaryContainer,
    error = Dark_Error,
    onError = Dark_OnError,
    errorContainer = Dark_Error,
    onErrorContainer = Dark_OnError,
    background = Dark_Background,
    onBackground = Dark_OnBackground,
    surface = Dark_Surface,
    onSurface = Dark_OnSurface,
    surfaceVariant = Dark_SurfaceVariant,
    onSurfaceVariant = Dark_OnSurfaceVariant,
    outline = Dark_Outline,
    inverseOnSurface = Color.Black,
    inverseSurface = Color.White,
    inversePrimary = Light_Primary,
    surfaceTint = Dark_Primary,
    outlineVariant = Dark_Outline,
    scrim = Color.Black,
)

@Composable
fun TestAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // <--- CAMBIO CLAVE: Desactivar color dinámico
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Gestionar la barra de estado con la API que estés usando (accompanist o nativa)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}