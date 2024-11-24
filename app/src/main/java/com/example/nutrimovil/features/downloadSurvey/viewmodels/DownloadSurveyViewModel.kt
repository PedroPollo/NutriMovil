package com.example.nutrimovil.features.downloadSurvey.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrimovil.features.downloadSurvey.data.repository.DownloadedSurveysLocalRepository
import com.example.nutrimovil.features.surveys.data.models.Survey
import com.example.nutrimovil.io.APIService
import com.example.nutrimovil.io.EncuestasData
import com.example.nutrimovil.io.response.EncuestasResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class DownloadSurveyViewModel : ViewModel() {
    private val apiService: APIService by lazy {
        APIService.create()
    }

    private val _surveys = MutableStateFlow<List<Survey>?>(null) // Estado observable
    val surveys: StateFlow<List<Survey>?> = _surveys

    val repository = DownloadedSurveysLocalRepository()

    fun createJson(context: Context, id: String) {
        repository.createJSON(context, id)
    }

    fun downloadSurvey(context: Context, survey: Survey, id: String){
        repository.downloadSurvey(context = context, survey = survey, id = id)
    }

    fun getSurveys(context: Context, id: String): MutableList<Survey> {
        return repository.getDownloadedSurveys(context = context, encuestador = id)
    }

    fun getSurveysFromCloud(idList: List<String?>) {
        viewModelScope.launch {
            try {
                val response = apiService.getEncuestas(EncuestasData(idList))
                response.enqueue(object : Callback<EncuestasResponse> {
                    override fun onResponse(
                        call: Call<EncuestasResponse>,
                        response: Response<EncuestasResponse>
                    ) {
                        if (response.isSuccessful) {
                            val encuestasResponse = response.body()
                            _surveys.value = encuestasResponse?.body as List<Survey>?
                        }
                    }

                    override fun onFailure(call: Call<EncuestasResponse>, t: Throwable) {
                        Log.d("Servidor", "No se pudo conectar", t)
                        _surveys.value = emptyList() // Manejo de error
                    }
                })
            } catch (e: IOException) {
                Log.e("ViewModel", "Error", e)
                _surveys.value = emptyList() // Manejo de error
            }
        }
    }
}