package org.utl.testapp.ui.fragments

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import org.utl.testapp.ui.components.WaterLevelIndicator
import org.utl.testapp.ui.componets.PHMeterScreen
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import org.utl.testapp.network.wifi.RetrofitClient
import org.utl.testapp.ui.entity.SensorData
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun HomeFragment(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    var sensorData by remember { mutableStateOf<SensorData?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        while (true) {
            val response = RetrofitClient.getSensorData(context)
            sensorData = if (response?.isSuccessful == true) response.body() else null
            delay(3000L)
        }
    }

    val nivelInt = sensorData?.nivel ?: 1  // valor 0-3 (Int)

    // Mapa de niveles a porcentaje
    val nivelMap = mapOf(
        0 to 0f,
        1 to 30f,
        2 to 60f,
        3 to 100f
    )

    // Obtener el porcentaje según el nivel, si no existe usar 0f
    val nivelActual = nivelMap[nivelInt] ?: 0f

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PHMeterScreen(phLevel = sensorData?.pH?.toFloat() ?: 7.0f)

        Spacer(modifier = Modifier.height(32.dp))

        // Aquí aplicas un overlay o fondo de color usando Modifier.drawBehind o background
        WaterLevelIndicator(nivel = sensorData?.nivel ?: 0)
    }
}