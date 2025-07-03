package org.utl.testapp

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import kotlinx.coroutines.delay

import org.utl.testapp.ui.theme.TestAppTheme
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import org.utl.testapp.network.udp.UdpClient
import org.utl.testapp.ui.componets.AppBottomNavigationBar
import org.utl.testapp.ui.componets.AppHeader
import org.utl.testapp.ui.componets.RequestPermissions
import org.utl.testapp.ui.componets.SplashScreen
import org.utl.testapp.ui.fragments.AnalyticFragment
import org.utl.testapp.ui.fragments.ConfigFragment
import org.utl.testapp.ui.fragments.HomeFragment

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@SuppressLint("ContextCastToActivity")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val activity = LocalContext.current as Activity

    var showSplashScreen by remember { mutableStateOf(true) }
    var hasPermission by remember { mutableStateOf(false) }

    var selectedScreen by remember { mutableStateOf(Screen.PH_METER) }
    var isDarkThemeManuallyEnabled by remember { mutableStateOf<Boolean?>(null) }
    val currentDarkTheme = isDarkThemeManuallyEnabled ?: isSystemInDarkTheme()
    var showThemeToggleButton by remember { mutableStateOf(true) }

    if (!hasPermission) {
        RequestPermissions(activity = activity) {
            hasPermission = true
        }
    } else {
        // Mostrar splash solo después de permisos
        LaunchedEffect(Unit) {
            delay(2000)
            showSplashScreen = false
        }

        UdpClient.discoverEsp32AndShowToast(activity)

        TestAppTheme(darkTheme = currentDarkTheme) {
            Crossfade(targetState = showSplashScreen) { showSplash ->
                if (showSplash) {
                    SplashScreen { showSplashScreen = false }
                } else {
                    Scaffold(
                        topBar = {
                            val headerText = when (selectedScreen) {
                                Screen.PH_METER -> "Medidor de pH"
                                Screen.SETTINGS -> "Ajuste de Agua"
                                Screen.ANALYTICS -> "Clasificación del agua"
                            }
                            AppHeader(text = headerText)
                        },
                        bottomBar = {
                            AppBottomNavigationBar(
                                selectedScreen = selectedScreen,
                                onScreenSelected = { screen -> selectedScreen = screen },
                                currentDarkTheme = currentDarkTheme,
                                onToggleTheme = { isDarkThemeManuallyEnabled = !currentDarkTheme },
                                showThemeToggle = showThemeToggleButton
                            )
                        }
                    ) { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            when (selectedScreen) {
                                Screen.PH_METER -> HomeFragment()
                                Screen.SETTINGS -> ConfigFragment()
                                Screen.ANALYTICS -> AnalyticFragment()
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class Screen {
    PH_METER,
    SETTINGS,
    ANALYTICS
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    TestAppTheme {
        MainScreen()
    }
}