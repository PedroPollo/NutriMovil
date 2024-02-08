package com.example.nutrimovil.features.surveys.data.repository

import com.example.nutrimovil.features.surveys.data.models.Question
import com.example.nutrimovil.features.surveys.data.models.Survey
import com.example.nutrimovil.features.surveys.data.models.TypeQuestion

interface SurveyRepository {
    fun getSurvey(name: String): Survey
    fun getSurveysName(): List<String>
}

class SurveyLocalRepository : SurveyRepository {
    private val surveys: List<Survey> = listOf(
        Survey(
            "1",
            "Primer cuestionario",
            listOf(
                Question(
                    "Cual es tu color favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Cual es tu color menos favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Que nivel estudias",
                    listOf("Primaria", "Secundaria", "Preparatoria", "Licenciatura"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Cual es tu materia favorita",
                    listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Quiero una extra",
                    listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Nombre",
                    null,
                    TypeQuestion.OPEN
                ),
                Question(
                    "Peso (Kg)",
                    null,
                    TypeQuestion.OPEN
                ),
                Question(
                    "Altura (Cm)",
                    null,
                    TypeQuestion.OPEN
                )
            )
        ),
        Survey(
            "2",
            "Segunda encuesta",
            listOf(
                Question(
                    "Cual es tu color favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Cual es tu color menos favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Que nivel estudias",
                    listOf("Primaria", "Secundaria", "Preparatoria", "Licenciatura"),
                    TypeQuestion.CLOSE
                ),
                Question(
                    "Cual es tu materia favorita",
                    listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    TypeQuestion.CLOSE
                )
            )
        )
    )

    override fun getSurvey(name: String): Survey {
        return surveys.find { it.name == name }!!
    }

    override fun getSurveysName(): List<String> {
        val list = mutableListOf<String>()
        for (survey in surveys) {
            list.add(survey.name)
        }
        return list
    }
}