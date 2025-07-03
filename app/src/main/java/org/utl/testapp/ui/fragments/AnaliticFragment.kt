package org.utl.testapp.ui.fragments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import org.utl.testapp.R // Asegúrate de que esto se resuelva para tus recursos

@Composable
fun AnalyticFragment(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Imagen "probetas.png"
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Probetas de clasificación de agua",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Altura de la imagen
                .padding(vertical = 16.dp)
        )

        // Leyenda "Ácido - Neutro - Alcalino"
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Ácido", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            Text(text = "Neutro", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            Text(text = "Alcalino", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Sección de Válvulas
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Válvula OK",
                    tint = Color.Green,
                    modifier = Modifier.size(48.dp)
                )
                Text(text = "OK", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            }
            Text(text = "Válvulas", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onBackground)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Válvula Open",
                    tint = Color.Red,
                    modifier = Modifier.size(48.dp)
                )
                Text(text = "Open", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Sección de Uso recomendado del agua
        Text(
            text = "Uso recomendado del agua",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Contenedor de la tabla con bordes
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(1.dp, MaterialTheme.colorScheme.onBackground)
        ) {
            val borderColor = MaterialTheme.colorScheme.onBackground

            // Fila 1: 3 - 6.4 | No potable/Baño
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "3 - 6.4",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, borderColor)
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
                Text(
                    text = "No potable/Baño",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1.5f)
                        .border(1.dp, borderColor)
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }
            // Fila 2: 6.5 - 7.5 | Potable/Riego
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "6.5 - 7.5",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, borderColor)
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
                Text(
                    text = "Potable/Riego",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1.5f)
                        .border(1.dp, borderColor)
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }
            // Fila 3: 7.6 - 14 | Uso industrial
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "7.6 - 14",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, borderColor)
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
                Text(
                    text = "Uso industrial",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(1.5f)
                        .border(1.dp, borderColor)
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}