package org.utl.testapp.ui.componets

import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import android.app.Activity
import android.graphics.Color
import androidx.compose.runtime.Composable

@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = color.toArgb()  // Aunque está deprecated, sigue funcionando

        val insetsController = WindowCompat.getInsetsController(window, view)
        insetsController.isAppearanceLightStatusBars = darkIcons
    }
}