package com.example.nutrimovil.features.surveys.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.nutrimovil.features.surveys.ui.screens.ToggleableInfo
import com.example.nutrimovil.ui.theme.PrimarioVar
import com.example.nutrimovil.ui.theme.SecundarioVar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionViewOpen(pregunta: String, state: SnapshotStateList<ToggleableInfo>) {
    var text by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .clickable { isExpanded = !isExpanded },
        color = PrimarioVar,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = pregunta, fontFamily = FontFamily.Monospace)
            if (isExpanded) {
                state.forEachIndexed { _, info ->
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedBorderColor = SecundarioVar,
                            unfocusedBorderColor = SecundarioVar,
                            focusedLabelColor = SecundarioVar,
                        ),
                        modifier = Modifier.padding(5.dp),
                        label = { Text(text = "Respuesta") },
                        value = text,
                        textStyle = TextStyle(color = Color.Black, fontFamily = FontFamily.Monospace),
                        onValueChange = {
                            text = it
                            state.replaceAll {
                                it.copy(
                                    isChecked = it.text == info.text,
                                    text = text
                                )
                            }
                        })
                }
                FloatingActionButton(
                    modifier = Modifier.padding(10.dp),
                    onClick = { isExpanded = !isExpanded },
                    containerColor = SecundarioVar
                ) {
                    Text(text = "Siguiente")
                }
            }
        }
    }
}

@Composable
fun QuestionViewClose(pregunta: String, state: SnapshotStateList<ToggleableInfo>) {
    var isExpanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp)
            .clickable { isExpanded = !isExpanded },
        color = PrimarioVar,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = pregunta, fontFamily = FontFamily.Monospace)
            if (isExpanded) {
                state.forEachIndexed { _, info ->
                    ListItem(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .clickable {
                                isExpanded = !isExpanded
                                state.replaceAll {
                                    it.copy(
                                        isChecked = it.text == info.text
                                    )
                                }
                            },
                        colors = ListItemDefaults.colors(containerColor = SecundarioVar),
                        headlineContent = {
                            Text(text = info.text)
                        },
                        trailingContent = {
                            RadioButton(
                                selected = info.isChecked,
                                onClick = {
                                    isExpanded = !isExpanded
                                    state.replaceAll {
                                        it.copy(
                                            isChecked = it.text == info.text
                                        )
                                    }
                                })
                        }
                    )
                }
                FloatingActionButton(
                    modifier = Modifier.padding(10.dp),
                    onClick = { isExpanded = !isExpanded },
                    containerColor = SecundarioVar
                ) {
                    Text(text = "Siguiente")
                }
            }
        }
    }
}