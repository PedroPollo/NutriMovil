package com.example.nutrimovil.features.surveys.data.models

data class Survey(
    val _id: String,
    val nombre: String,
    val descripcion: String,
    val preguntas: List<Question>,
    val idInvestigador: String
)
