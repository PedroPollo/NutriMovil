package com.example.nutrimovil.features.surveys.data.models

import com.google.gson.annotations.SerializedName

data class Survey(
    val _id: String,
    val nombre: String,
    val descripcion: String,
    val autor: String,
    val preguntas: List<Pregunta>
)


data class ListSurvey(
    @SerializedName("body")
    var data: List<Survey> = listOf()
)
