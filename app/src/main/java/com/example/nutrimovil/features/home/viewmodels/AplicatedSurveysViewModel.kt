package com.example.nutrimovil.features.home.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.nutrimovil.features.home.data.repository.AplicatedSurveysLocalRepository
import com.example.nutrimovil.features.surveys.data.models.SurveyResponse

class AplicatedSurveysViewModel : ViewModel() {
    val repository = AplicatedSurveysLocalRepository()

    fun getSurveys(encuestador: String, context: Context): MutableList<SurveyResponse>{
        return repository.getAplicatedSurveys(encuestador = encuestador, context = context)
    }

    fun putSurvey(aplicatedSurvey: String, context: Context, id: String){
        repository.addAplicatedSurvey(aplicatedSurvey = aplicatedSurvey, context = context, id = id)
    }

    fun createJSON(context: Context, id: String){
        repository.createJSON(context = context, id = id)
    }

    fun uploadJSON(context: Context, id:String){
        repository.uploadJson(context = context, id = id)
    }

}