package com.example.nutrimovil.features.downloadSurvey.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.features.downloadSurvey.viewmodels.DownloadSurveyViewModel
import com.example.nutrimovil.features.surveys.data.models.Survey
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import com.example.nutrimovil.ui.theme.PrimarioVar
import com.example.nutrimovil.ui.theme.SecundarioVar

class DownloadSurveyActivity : ComponentActivity() {
    private val downloadSurveyViewModel: DownloadSurveyViewModel by viewModels()
    private val us = Us.getUser()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMovilTheme {
                downloadSurveyViewModel.getSurveysFromCloud(us!!.researchers)
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Fondo
                ) {
                    DownloadableSurveys(context = this, id = us.id)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadableSurveys(
    context: DownloadSurveyActivity,
    downloadSurveyViewModel: DownloadSurveyViewModel = viewModel(),
    id: String
) {
    val surveys by downloadSurveyViewModel.surveys.collectAsState() // Observa el estado

    if (surveys == null) {
        // Mostrar indicador de carga mientras los datos se están obteniendo
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = SecundarioVar
                    ),
                    title = {
                        Text(
                            text = "Encuestas para descargar",
                            fontFamily = FontFamily.Monospace
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { context.finish() }) {
                            Icon(Icons.Filled.ArrowBack, "Regresar")
                        }
                    }
                )
            }
        ) {
            var selectedSurvey: Survey? by remember { mutableStateOf(null) } // Para almacenar la encuesta seleccionada
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var isExpanded by remember {
                    mutableStateOf(false)
                }
                var selectedItem by remember {
                    mutableStateOf("")
                }
                Text("Seleccione una encuesta para descargar")
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = it }) {
                    TextField(
                        value = selectedItem,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        if (surveys != null) {
                            for (label in surveys!!) {
                                DropdownMenuItem(
                                    text = { Text(text = label.nombre) },
                                    onClick = {
                                        selectedItem = label.nombre
                                        isExpanded = false
                                        selectedSurvey = label
                                    })
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "check your internet connection",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                Button(
                    modifier = Modifier.padding(bottom = 30.dp),
                    colors = ButtonDefaults.buttonColors(PrimarioVar),
                    onClick = {
                        selectedSurvey?.let { survey ->
                            downloadSurveyViewModel.downloadSurvey(
                                context = context,
                                survey = survey,
                                id = id
                            )
                            Toast.makeText(context, "Encuesta descargada", Toast.LENGTH_SHORT)
                                .show()
                            context.finish()
                        } ?: Toast.makeText(
                            context,
                            "Seleccione una encuesta primero",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    Text("Descargar encuesta", fontFamily = FontFamily.Monospace)
                }
            }
        }
    }
}