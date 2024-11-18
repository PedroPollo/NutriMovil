package com.example.nutrimovil.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrimovil.data.models.User
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.io.APIService
import com.example.nutrimovil.io.LoginData
import com.example.nutrimovil.io.response.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class LoginViewModel : ViewModel() {
    private val apiService: APIService by lazy {
        APIService.create()
    }
    var success = mutableStateOf(false)
    var userG: User? = null

    fun login(user: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.postLogin(LoginData(user, password))
                response.enqueue(object: retrofit2.Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful){
                            val loginResponse = response.body()
                            if (loginResponse == null){
                                Log.d("Server side","An error ocurred in server")
                            } else {
                                if (loginResponse.error) {
                                    Log.e("Login","Credenciales incorrectas")
                                } else {
                                    success.value = true
                                    userG = loginResponse.body
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("Servidor", "No se pudo conectar", t)
                    }

                })
            } catch (e: IOException){
                Log.e("ViewModel", "Error", e)
            }
        }
    }

    fun closeUser(){
        this.userG = null
        success.value = false
        println()
    }

    fun setUser() {
        val us = Us.getUser()
        this.userG = us
    }
}