package com.example.nutrimovil.features.surveys.data.repository

import com.example.nutrimovil.features.surveys.data.models.Question
import com.example.nutrimovil.features.surveys.data.models.Survey

interface SurveyRepository {
    fun getSurvey(name: String): Survey
    fun getSurveysName(): List<String>
}

class SurveyLocalRepository : SurveyRepository {
    private val surveys: List<Survey> = listOf(
        Survey(
            "1",
            "Primer cuestionario",
            "Encuesta de prueba para la aplicacion mobil",
            listOf(
                Question(
                    "Cual es tu color favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Cual es tu color menos favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Que nivel estudias",
                    listOf("Primaria", "Secundaria", "Preparatoria", "Licenciatura"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Cual es tu materia favorita",
                    listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Quiero una extra",
                    listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Nombre",
                    null,
                    "abierta",
                    _id = "id_prueba"
                ),
                Question(
                    "Peso (Kg)",
                    null,
                    "abierta",
                    _id = "id_prueba"
                ),
                Question(
                    "Altura (Cm)",
                    null,
                    "abierta",
                    _id = "id_prueba"
                )
            ),
            "67391dd221e10b6cb5c7705f"
        ),
        Survey(
            "2",
            "Segunda encuesta",
            "Encuesta de prueba para la aplicacion movil",
            listOf(
                Question(
                    "Cual es tu color favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Cual es tu color menos favorito",
                    listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Que nivel estudias",
                    listOf("Primaria", "Secundaria", "Preparatoria", "Licenciatura"),
                    "opcion-multiple",
                    _id = "id_prueba"
                ),
                Question(
                    "Cual es tu materia favorita",
                    listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    "opcion-multiple",
                    _id = "id_prueba"
                )
            ),
            "67391dd221e10b6cb5c7705f"
        )
    )

    override fun getSurvey(name: String): Survey {
        return surveys.find { it.nombre == name }!!
    }

    override fun getSurveysName(): List<String> {
        val list = mutableListOf<String>()
        for (survey in surveys) {
            list.add(survey.nombre)
        }
        return list
    }
}