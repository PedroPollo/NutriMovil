package com.example.nutrimovil.features.downloadSurvey.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import com.example.nutrimovil.ui.theme.SecundarioVar

class DownloadSurveyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Fondo
                ) {
                    val lista = listOf(
                        arrayOf("Primera encuesta", "id investigador"),
                        arrayOf("Segunda encuesta", "2")
                    )
                    DownloadableSurveys(lista = lista, this)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadableSurveys(
    lista: List<Array<String>>,
    context: DownloadSurveyActivity
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SecundarioVar
                ),
                title = { Text(text = "Encuestas para descargar", fontFamily = FontFamily.Monospace) },
                navigationIcon = {
                    IconButton(onClick = { context.finish() }) {
                        Icon(Icons.Filled.ArrowBack, "Regresar")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(lista) { item ->
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = item[0])
                    }
                }
            }
        }
    }
}