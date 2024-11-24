package com.example.nutrimovil.features.home.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrimovil.features.home.data.repository.AplicatedSurveysLocalRepository
import com.example.nutrimovil.features.surveys.data.models.SurveyResponse
import com.example.nutrimovil.io.APIService
import com.example.nutrimovil.io.EncuestasContestadas
import com.example.nutrimovil.io.response.ShowableResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        // Obtén el contenido del JSON desde el archivo
        val body = repository.uploadJson(context, id)

        // Deserializa el JSON a la clase EncuestasContestadas
        val encuestasContestadas = deserializarEncuestasContestadas(body)

        // Verifica si la deserialización fue exitosa
        if (encuestasContestadas != null) {
            // Si el JSON es válido y fue deserializado correctamente, realiza la llamada a la API
            viewModelScope.launch {
                try {
                    val response = apiService.uploadEncuestas(encuestasContestadas)
                    response.enqueue(object : Callback<ShowableResponse> {
                        override fun onResponse(
                            call: Call<ShowableResponse>,
                            response: Response<ShowableResponse>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "Datos agregados con éxito", Toast.LENGTH_SHORT).show()
                                repository.borrarJson(context, id)
                            } else {
                                // Si la respuesta de la API no fue exitosa, manejar el error
                                Toast.makeText(context, "Error al enviar los datos", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<ShowableResponse>, t: Throwable) {
                            Log.d("Servidor", "No se pudo conectar", t)
                            Toast.makeText(context, "No se pudo conectar con el servidor", Toast.LENGTH_LONG).show()
                        }

                    })
                } catch (e: IOException) {
                    Log.e("ViewModel", "Error: ", e)
                    Toast.makeText(context, "No se pudo conectar con el servidor 2", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            // Si la deserialización falló, muestra un mensaje de error
            Toast.makeText(context, "El archivo JSON no es válido", Toast.LENGTH_LONG).show()
            Log.e("uploadSurveys", "Error en la deserialización del JSON.")
        }
    }

    private fun deserializarEncuestasContestadas(json: String): EncuestasContestadas? {
        return try {
            val gson = Gson()
            // Define el tipo de la clase que quieres deserializar
            val tipo = object : TypeToken<EncuestasContestadas>() {}.type
            // Deserializa el JSON al objeto EncuestasContestadas
            gson.fromJson(json, tipo)
        } catch (e: Exception) {
            e.printStackTrace()
            null  // Si ocurre un error, retorna null
        }
    }

}