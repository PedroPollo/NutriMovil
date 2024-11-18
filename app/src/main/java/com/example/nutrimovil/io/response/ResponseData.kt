package com.example.nutrimovil.io.response

import com.example.nutrimovil.data.models.User
import com.example.nutrimovil.features.surveys.data.models.Survey

data class LoginResponse(
    val error: Boolean,
    val status: Int,
    val body: User
)

data class InvestigadoresResponse(
    val error: Boolean,
    val status: Int,
    val body: List<Investigador>
)

data class Investigador(
    val id: Int,
    val nombre: String
)

data class EncuestasResponse(
    val error: Boolean,
    val status: Int,
    val body: List<Survey?>
)

data class ShowableResponse(
    val error: Boolean,
    val status: Int,
    val body: String
)
