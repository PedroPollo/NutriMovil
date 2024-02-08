package com.example.nutrimovil.features.surveys.data.models


enum class TypeQuestion {
    OPEN,
    CLOSE
}

data class Question(
    val text: String,
    val response: List<String>?,
    val questionType: TypeQuestion
)
