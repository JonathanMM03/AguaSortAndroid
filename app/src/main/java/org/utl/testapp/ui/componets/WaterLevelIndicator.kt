package org.utl.testapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.utl.testapp.ui.theme.Light_Tertiary

// Importa los colores específicos de tu tema
import org.utl.testapp.ui.theme.PrimaryDesignColor
import org.utl.testapp.ui.theme.SurfaceDesignColor

@Composable
fun WaterLevelIndicator(
    nivel: Int,  // Nivel entero 0-3
    modifier: Modifier = Modifier,
    testing: Boolean = false
) {
    // Convierte nivel (0-3) a porcentaje (0-100)
    val targetPercent = ((nivel.coerceIn(0, 3).toFloat() / 3f) * 100f)

    var waterLevel by remember { mutableStateOf(targetPercent) }

    val animatedWaterLevel by animateFloatAsState(
        targetValue = targetPercent,
        animationSpec = tween(durationMillis = 1000),
        label = "Water Level Animation"
    )

    val wavePhase = remember { Animatable(0f) }

    // Animación infinita para la onda
    LaunchedEffect(Unit) {
        while (true) {
            wavePhase.animateTo(
                targetValue = 2 * Math.PI.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing)
                )
            )
            wavePhase.snapTo(0f)
        }
    }

    Column(
        modifier = modifier
            .width(200.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cantidad de agua",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(SurfaceDesignColor)
                .border(2.dp, PrimaryDesignColor, MaterialTheme.shapes.medium)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val waterHeight = height * (animatedWaterLevel / 100f)

                // Ondas animadas
                val wavePath = Path().apply {
                    moveTo(0f, height - waterHeight)
                    val amplitude = height * 0.02f
                    val frequency = 2f

                    for (x in 0..width.toInt()) {
                        val fx = x.toFloat()
                        val y = (height - waterHeight) + amplitude * kotlin.math.sin((fx / width) * frequency * 2f * Math.PI + wavePhase.value).toFloat()
                        lineTo(fx, y)
                    }

                    lineTo(width, height)
                    lineTo(0f, height)
                    close()
                }

                clipPath(Path().apply { addRect(androidx.compose.ui.geometry.Rect(0f, 0f, width, height)) }, clipOp = ClipOp.Intersect) {
                    drawPath(path = wavePath, color = PrimaryDesignColor)
                }

                // Burbujas dinámicas
                drawCircle(
                    color = Color.White.copy(alpha = 0.6f),
                    radius = 3.dp.toPx(),
                    center = Offset(width * 0.3f, height - waterHeight - 12.dp.toPx())
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.4f),
                    radius = 4.dp.toPx(),
                    center = Offset(width * 0.7f, height - waterHeight - 24.dp.toPx())
                )
            }

            // Texto animado de porcentaje
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.Black.copy(alpha = 0.3f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${animatedWaterLevel.toInt()} %",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        if (testing) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Nivel de Agua: ${animatedWaterLevel.toInt()}%", color = MaterialTheme.colorScheme.onBackground)
            // Opcional: slider para probar porcentaje
            Slider(
                value = animatedWaterLevel,
                onValueChange = { newValue ->
                    waterLevel = newValue
                },
                valueRange = 0f..100f,
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