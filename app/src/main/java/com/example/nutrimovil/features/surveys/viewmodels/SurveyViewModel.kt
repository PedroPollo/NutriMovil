package com.example.nutrimovil.features.surveys.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nutrimovil.features.surveys.data.models.Survey
import com.example.nutrimovil.features.surveys.data.repository.SurveyLocalRepository

class SurveyViewModel: ViewModel() {
    val surveyRepository = SurveyLocalRepository()

    fun getSurvey(name: String): Survey{
        return surveyRepository.getSurvey(name)
    }

    fun getSurveysName(): List<String>{
        return surveyRepository.getSurveysName()
    }
}