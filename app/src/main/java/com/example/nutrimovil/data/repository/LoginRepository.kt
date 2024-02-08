package com.example.nutrimovil.data.repository

import com.example.nutrimovil.data.models.User

interface LoginRepository {
    fun getUser(user: String, password: String): User
    fun getUser(id: String): User
    fun login(user: String, password: String): Boolean
    fun getAccepted(id: String): Boolean
}

class LoginLocalRepository : LoginRepository {
    override fun getUser(user: String, password: String): User {
        return UsersLocalRepository().users.find { it.username == user && it.password == password }!!
    }

    override fun getUser(id: String): User {
        return UsersLocalRepository().users.find { it.id == id }!!
    }

    override fun login(user: String, password: String): Boolean {
        return UsersLocalRepository().users.find { it.username == user; it.password == password } != null
    }

    override fun getAccepted(id: String): Boolean {
        return UsersLocalRepository().users.find { it.id == id }?.isAccepted == true
    }

}

