package com.example.nutrimovil.features.surveys.data.models

data class Survey(
    val id: String,
    val name: String,
    val questions: List<Question>
)
