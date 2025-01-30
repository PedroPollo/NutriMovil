package com.example.nutrimovil.features.surveys.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.features.home.ui.screens.HomeActivity
import com.example.nutrimovil.features.home.viewmodels.AplicatedSurveysViewModel
import com.example.nutrimovil.features.surveys.data.models.QuestionResponse
import com.example.nutrimovil.features.surveys.data.models.SurveyResponse
import com.example.nutrimovil.features.surveys.ui.components.QuestionViewClose
import com.example.nutrimovil.features.surveys.ui.components.QuestionViewOpen
import com.example.nutrimovil.features.surveys.viewmodels.SurveyViewModel
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import com.example.nutrimovil.ui.theme.SecundarioVar
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar


class SurveysActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Fondo
                ) {
                    val id = intent.getStringExtra("id")
                    val encuestador = intent.getStringExtra("Encuestador")
                    ShowSurvey(id = id!!, context = this, encuestador = encuestador)
                }
            }
        }
    }
}

data class ToggleableInfo(
    val isChecked: Boolean,
    val text: String
)

data class QuestionAndResponses(
    val text: String,
    val id: String,
    val toggleableInfo: SnapshotStateList<ToggleableInfo>,
    val questionType: String
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType", "SimpleDateFormat")
@Composable
fun ShowSurvey(
    surveyViewModel: SurveyViewModel = viewModel(),
    id: String,
    context: SurveysActivity,
    encuestador: String?,
    aplicatedSurveysViewModel: AplicatedSurveysViewModel = viewModel(),
) {
    val intent = Intent(context, HomeActivity::class.java)
    val encuesta = surveyViewModel.getSurvey(id = id, encuestador = encuestador!!, context = context)
    val preguntas: MutableList<QuestionAndResponses> = mutableListOf()

    for (question in encuesta.preguntas) {
        preguntas += QuestionAndResponses(
            text = question.texto,
            toggleableInfo = remember {
                mutableStateListOf<ToggleableInfo>().apply {
                    question.opciones?.let { state ->
                        addAll(state.map {
                            ToggleableInfo(isChecked = false, it)
                        })
                    } ?: add(ToggleableInfo(isChecked = true, ""))
                }
            },
            questionType = question.tipo,
            id = question._id
        )
    }

    val responses: MutableList<QuestionResponse> = mutableListOf()
    lateinit var surveyResponse: SurveyResponse
    val gson = Gson()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SecundarioVar
                ),
                title = { Text(encuesta.nombre, fontFamily = FontFamily.Monospace) },
                navigationIcon = {
                    IconButton(onClick = { context.finish() }) {
                        Icon(Icons.Filled.ArrowBack, "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        var isValid = true

                        for (pregunta in preguntas) {
                            val respuesta = pregunta.toggleableInfo.find { it.isChecked }?.text
                            if (pregunta.questionType == "opcion-multiple" && respuesta.isNullOrEmpty()) {
                                isValid = false
                                break
                            }
                            if (pregunta.questionType == "abierta" && respuesta.isNullOrBlank()) {
                                isValid = false
                                break
                            }
                            responses += QuestionResponse(
                                pregunta.id,
                                respuesta
                            )
                        }

                        if (isValid) {
                            surveyResponse = SurveyResponse(
                                id = encuesta._id,
                                encuestador = encuestador.toString(),
                                questionResponse = responses,
                                fecha = SimpleDateFormat("dd-MM-yyyy HH:mm").format(Calendar.getInstance().time),
                                encuesta = encuesta.nombre
                            )
                            aplicatedSurveysViewModel.putSurvey(
                                gson.toJson(surveyResponse),
                                context,
                                Us.getUser()!!.id
                            )
                            context.startActivity(intent)
                            context.finish()
                        } else {
                            Toast.makeText(
                                context,
                                "Por favor, responde todas las preguntas antes de guardar.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Guardar"
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 60.dp)
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(preguntas) { item ->
                if (item.questionType == "opcion-multiple") {
                    QuestionViewClose(item.text, item.toggleableInfo)
                } else {
                    QuestionViewOpen(pregunta = item.text, state = item.toggleableInfo)
                }
            }
        }
    }
}
