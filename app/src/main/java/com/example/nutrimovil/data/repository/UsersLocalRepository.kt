package com.example.nutrimovil.data.repository

import com.example.nutrimovil.data.models.User

class UsersLocalRepository {
    val users = listOf(
        User(
            "1",
            "Pedro Alejandro Nunez Perez",
            "pedropollo",
            "P3dro1234",
            "1234",
            listOf(null),
            false
        ),
        User(
            "2",
            "David",
            "david",
            "1234",
            "323232",
            listOf("Profesor 1"),
            true
        ),
        User(
            "3",
            "Juan Ignacio",
            "ji",
            "1234",
            "323232",
            listOf("Profesor 1"),
            true
        )
    )
}