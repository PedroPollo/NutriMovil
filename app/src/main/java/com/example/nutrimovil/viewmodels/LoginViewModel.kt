package com.example.nutrimovil.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.nutrimovil.data.models.User
import com.example.nutrimovil.data.repository.LoginLocalRepository

class LoginViewModel : ViewModel() {
    private val loginRepository = LoginLocalRepository()
    var success = mutableStateOf(false)
    var user: User? = null


    fun login(user: String, password: String) {
        success.value = loginRepository.login(user, password)
        this.user = loginRepository.getUser(user, password)
    }

    fun getAccepted(): Boolean {
        return loginRepository.getAccepted(this.user!!.id)
    }

    fun closeUser(){
        this.user = null
        success.value = false
    }

    fun setUser(id: String) {
        val us = loginRepository.getUser(id)
        this.user = us
    }
}