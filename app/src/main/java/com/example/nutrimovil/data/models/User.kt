package com.example.nutrimovil.data.models

data class User(
    val id: String,
    val nombre: String,
    val usuario: String,
    val password: String,
    val matricula: String,
    val researchers: List<String?>,
    val isAccepted: Boolean,
    val token: String
)
