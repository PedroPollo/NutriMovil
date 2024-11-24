package com.example.nutrimovil.features.surveys.data.models

data class Pregunta(
    val texto: String,
    val tipo: String,
    val opciones: List<String>?,
    val _id: String
)
