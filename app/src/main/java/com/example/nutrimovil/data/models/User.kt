package com.example.nutrimovil.data.models

data class User(
    val id: String,
    val nombre: String,
    val username: String,
    val password: String,
    val matricula: String,
    val reaserchers: List<Any?>,
    val isAccepted: Boolean
)
