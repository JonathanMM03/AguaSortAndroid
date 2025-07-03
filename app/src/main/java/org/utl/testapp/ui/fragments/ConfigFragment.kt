package org.utl.testapp.ui.fragments

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.utl.testapp.R
import org.utl.testapp.network.wifi.RetrofitClient
import org.utl.testapp.ui.components.WaterLevelIndicator
import org.utl.testapp.ui.entity.SetNivelResponse
import retrofit2.Response
import kotlin.math.abs

@Composable
fun ConfigFragment(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val allowedLevelsPercent = listOf(30f, 60f, 100f) // Correspondencia a nivel 1,2,3
    val coroutineScope = rememberCoroutineScope()
    val animatedWaterLevel = remember { Animatable(allowedLevelsPercent[0]) } // inicia en 30%

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            val currentLevel = animatedWaterLevel.value
                            val nearest = allowedLevelsPercent.minByOrNull { abs(it - currentLevel) }
                            nearest?.let {
                                animatedWaterLevel.animateTo(it, tween(300))
                            }
                        }
                    },
                    onVerticalDrag = { _, dragAmount ->
                        coroutineScope.launch {
                            val density = this@pointerInput.density
                            val newLevel = (animatedWaterLevel.value - dragAmount / density)
                                .coerceIn(allowedLevelsPercent.first(), allowedLevelsPercent.last())
                            animatedWaterLevel.snapTo(newLevel)
                        }
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Nivel del agua",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(Modifier.height(24.dp))

        // Aquí conviertes porcentaje a nivel (0-3)
        val nivelActual = when (animatedWaterLevel.value) {
            30f -> 1
            60f -> 2
            100f -> 3
            else -> 1
        }

        WaterLevelIndicator(
            nivel = nivelActual,
            modifier = Modifier.fillMaxWidth(0.8f),
            testing = false
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    val response = RetrofitClient.setNivel(context, nivelActual)
                    println(if (response?.isSuccessful == true) "Nivel enviado con éxito" else "Error al enviar nivel")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth(0.6f).height(50.dp)
        ) {
            Text(text = "Enviar", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}