package com.example.nutrimovil.features.home.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrimovil.features.home.data.repository.AplicatedSurveysLocalRepository
import com.example.nutrimovil.features.surveys.data.models.SurveyResponse
import com.example.nutrimovil.io.APIService
import com.example.nutrimovil.io.response.ShowableResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AplicatedSurveysViewModel : ViewModel() {
    private val apiService: APIService by lazy {
        APIService.create()
    }
    val repository = AplicatedSurveysLocalRepository()

    fun getSurveys(encuestador: String, context: Context): MutableList<SurveyResponse> {
        return repository.getAplicatedSurveys(encuestador = encuestador, context = context)
    }

    fun putSurvey(aplicatedSurvey: String, context: Context, id: String) {
        repository.addAplicatedSurvey(aplicatedSurvey = aplicatedSurvey, context = context, id = id)
    }

    fun createJSON(context: Context, id: String) {
        repository.createJSON(context = context, id = id)
    }

    fun uploadSurveys(context: Context, id: String) {
        val body = repository.uploadJson(context = context, id = id)
        viewModelScope.launch {
            try {
                val response = apiService.uploadEncuestas(body)
                response.enqueue(object : Callback<ShowableResponse> {
                    override fun onResponse(
                        call: Call<ShowableResponse>,
                        response: Response<ShowableResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Datos agregados con exito", Toast.LENGTH_SHORT).show()
                            repository.borrarJson(context, id)
                        }
                    }

                    override fun onFailure(call: Call<ShowableResponse>, t: Throwable) {
                        Log.d("Servidor", "No se pudo conectar", t)
                        Toast.makeText(context, "No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
                    }

                })
            } catch (e: IOException){
                Log.e("ViewModel", "Error: ", e)
                Toast.makeText(context, "No se pudo conectar con el servidor 2", Toast.LENGTH_LONG).show()

            }
        }
    }

}