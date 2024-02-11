package com.example.nutrimovil.features.surveys.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.example.nutrimovil.features.surveys.data.models.TypeQuestion
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
    val toggleableInfo: SnapshotStateList<ToggleableInfo>,
    val questionType: TypeQuestion
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType", "SimpleDateFormat")
@Composable
fun ShowSurvey(
    surveyViewModel: SurveyViewModel = viewModel(),
    id: String,
    context: SurveysActivity,
    encuestador: String?,
    aplicatedSurveysViewModel: AplicatedSurveysViewModel = viewModel()
) {
    val intent = Intent(context, HomeActivity::class.java)
    val encuesta = surveyViewModel.getSurvey(id)
    val preguntas: MutableList<QuestionAndResponses> = mutableListOf()

    for (question in encuesta.questions) {
        preguntas += QuestionAndResponses(
            text = question.text,
            toggleableInfo = remember {
                mutableStateListOf<ToggleableInfo>().apply {
                    question.response?.let { state ->
                        addAll(state.map {
                            ToggleableInfo(isChecked = false, it)
                        })
                    } ?: add(ToggleableInfo(isChecked = true, ""))
                }
            },
            questionType = question.questionType
        )
    }

    val responses: MutableList<QuestionResponse> = mutableListOf()
    lateinit var surveyResponse: SurveyResponse
    val gson = Gson()


    println()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SecundarioVar
                ),
                title = { Text(encuesta.name, fontFamily = FontFamily.Monospace) },
                navigationIcon = {
                    IconButton(onClick = { context.finish() }) {
                        Icon(Icons.Filled.ArrowBack, "Regresar")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        for (pregunta in preguntas) {
                            responses += QuestionResponse(
                                pregunta.text,
                                pregunta.toggleableInfo.find { it.isChecked }?.text
                            )
                        }
                        surveyResponse = SurveyResponse(
                            id = encuesta.id,
                            name = encuesta.name,
                            encuestador = encuestador.toString(),
                            questionResponse = responses,
                            fecha = SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().time)
                        )
                        aplicatedSurveysViewModel.putSurvey(gson.toJson(surveyResponse), context, Us.getUser()!!.id)
                        context.startActivity(intent)
                        context.finish()
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
                if (item.questionType == TypeQuestion.CLOSE) {
                    QuestionViewClose(item.text, item.toggleableInfo)
                } else {
                    QuestionViewOpen(pregunta = item.text, state = item.toggleableInfo)
                }
            }
        }
    }
}
