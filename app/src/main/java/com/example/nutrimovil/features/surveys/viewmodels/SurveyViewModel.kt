package com.example.nutrimovil.features.surveys.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.nutrimovil.features.downloadSurvey.data.repository.DownloadedSurveysLocalRepository
import com.example.nutrimovil.features.surveys.data.models.Survey

class SurveyViewModel : ViewModel() {
    private val downloadedSurvey = DownloadedSurveysLocalRepository()

    fun getSurvey(id: String, encuestador: String, context: Context): Survey {
        return downloadedSurvey.applySurvey(
            id = id,
            encuestador = encuestador,
            context = context
        ).apply {
            // Ajusta las opciones de las preguntas
            preguntas.forEach { pregunta ->
                if (pregunta.opciones?.isEmpty() == true) {
                    pregunta.opciones = null
                }
            }
        }
    }

    fun getSurveysName(encuestador: String, context: Context): List<SurveyData> {
        val encuestas = downloadedSurvey.getDownloadedSurveys(encuestador, context)
        val list = mutableListOf<SurveyData>()
        for (survey in encuestas) {
            list.add(SurveyData(_id = survey._id, nombre = survey.nombre))
        }
        return list
    }
}

data class SurveyData(
    val _id: String,
    val nombre: String
)