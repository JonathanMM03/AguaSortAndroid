package org.utl.testapp.ui.componets

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.utl.testapp.ui.theme.Light_PrimaryContainer
import kotlin.math.*

@Composable
fun PHMeterScreen(
    modifier: Modifier = Modifier,
    phLevel: Float = 7.0f, // Valor externo con default
    testing: Boolean = false
) {
    // Solo conserva el estado interno si estás en modo de prueba
    var testPhLevel: Float by remember { mutableStateOf(phLevel) }
    val displayedPh = if (testing) testPhLevel else phLevel

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nivel de pH",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = canvasWidth / 2f
            val center = Offset(radius, radius)

            // Arco del fondo
            drawArc(
                color = Light_PrimaryContainer,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(0f, 0f),
                size = Size(canvasWidth, canvasHeight),
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )

            // Segmentos de colores
            val colors = listOf(
                Color(0xFFE57373), Color(0xFFFFB74D), Color(0xFFDCE775),
                Color(0xFF81C784), Color(0xFF4FC3F7), Color(0xFF64B5F6), Color(0xFF9575CD)
            )
            val segmentWidth = 180f / colors.size
            colors.forEachIndexed { index, color ->
                drawArc(
                    color = color,
                    startAngle = 180f + (index * segmentWidth),
                    sweepAngle = segmentWidth,
                    useCenter = false,
                    topLeft = Offset(0f, 0f),
                    size = Size(canvasWidth, canvasHeight),
                    style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Butt)
                )
            }

            // Aguja
            val angle = 180f + (phLevel / 14f * 180f)
            val needleX = radius + (radius * cos(Math.toRadians(angle.toDouble()))).toFloat()
            val needleY = radius + (radius * sin(Math.toRadians(angle.toDouble()))).toFloat()
            drawLine(
                color = Color.Black,
                start = center,
                end = Offset(needleX, needleY),
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
            drawCircle(color = Color.Black, radius = 8.dp.toPx(), center = center)
        }

        Text(
            text = "pH: ${String.format("%.1f", displayedPh)}",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        )

        if (testing) {
            Text(
                text = "Ajustar pH: ${String.format("%.1f", testPhLevel)}",
                color = MaterialTheme.colorScheme.onBackground
            )
            Slider(
                value = phLevel.toFloat(),
                onValueChange = { testPhLevel = it },
                valueRange = 0f..14f,
                steps = 139,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
        }
    }
}