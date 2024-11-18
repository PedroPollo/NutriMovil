package com.example.nutrimovil.features.surveys.data.models

data class Question(
    val texto: String,
    val opciones: List<String>?,
    val tipo: String,
    val _id: String
)
