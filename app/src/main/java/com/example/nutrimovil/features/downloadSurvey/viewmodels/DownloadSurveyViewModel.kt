package com.example.nutrimovil.features.downloadSurvey.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrimovil.features.surveys.data.models.Survey
import com.example.nutrimovil.io.APIService
import com.example.nutrimovil.io.response.EncuestasResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DownloadSurveyViewModel : ViewModel() {
    private val apiService: APIService by lazy {
        APIService.create()
    }

    var surveys: List<Survey?>? = null

    fun getSurveys(id: List<Any?>) {
        viewModelScope.launch {
            try {
                val response = apiService.getEncuestas()
                response.enqueue(object : Callback<EncuestasResponse> {
                    override fun onResponse(
                        call: Call<EncuestasResponse>,
                        response: Response<EncuestasResponse>
                    ) {
                        if (response.isSuccessful) {
                            val encuestasResponse = response.body()
                            println(encuestasResponse)
                            Log.d("Encuesta", encuestasResponse?.body.toString())
                            surveys = encuestasResponse?.body
                            println()
                        }
                    }

                    override fun onFailure(call: Call<EncuestasResponse>, t: Throwable) {
                        Log.d("Servidor", "No se pudo conectar", t)
                    }

                })
            } catch (e: IOException) {
                Log.e("ViewModel", "Error", e)
            }
        }
    }
}