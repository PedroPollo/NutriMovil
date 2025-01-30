package com.example.nutrimovil.features.surveys.data.models

import com.google.gson.annotations.SerializedName

data class SurveyResponse(
    @SerializedName("id_encuesta")
    val id: String,
    @SerializedName("id_encuestador")
    val encuestador: String,
    @SerializedName("fecha_aplicacion")
    val fecha: String,
    @SerializedName("respuestas")
    val questionResponse: List<QuestionResponse>,
    @SerializedName("encuesta")
    val encuesta: String
)

data class QuestionResponse(
    @SerializedName("id_pregunta")
    val id: String,
    @SerializedName("respuesta")
    var response: String?
)

data class ListSurveyResponse(
    @SerializedName("data")
    var data: List<SurveyResponse> = listOf()
)