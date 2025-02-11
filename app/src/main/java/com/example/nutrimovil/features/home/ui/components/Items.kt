package com.example.nutrimovil.features.home.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrimovil.ui.theme.SecundarioVar

@Composable
fun Item(dia: String, encuesta: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        color = SecundarioVar
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "Person")
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = encuesta,
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = FontFamily.Default
                        ),
                        text = dia,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun showItem() {
    Item(dia = "29-01-2025 20:23", encuesta = "Nutricion en embarazadas")
}
