package com.example.nutrimovil.features.surveys.data.models

import com.google.gson.annotations.SerializedName

data class SurveyResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("encuestador")
    val encuestador: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("questionResponse")
    val questionResponse: List<QuestionResponse>
)

data class QuestionResponse(
    @SerializedName("text")
    val text: String,
    @SerializedName("response")
    var response: String?
)

data class ListSurveyResponse(
    @SerializedName("data")
    var data: List<SurveyResponse> = listOf()
)