package com.example.nutrimovil.features.surveys.data.repository

import com.example.nutrimovil.features.surveys.data.models.Pregunta
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
            "67391dd221e10b6cb5c7705f",
            listOf(
                Pregunta(
                    "Cual es tu color favorito",
                    opciones = listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Cual es tu color menos favorito",
                    opciones =listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Que nivel estudias",
                    opciones =listOf("Primaria", "Secundaria", "Preparatoria", "Licenciatura"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Cual es tu materia favorita",
                    opciones = listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Quiero una extra",
                    opciones = listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Nombre",
                    opciones = null,
                    tipo = "abierta",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Peso (Kg)",
                    opciones = null,
                    tipo = "abierta",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Altura (Cm)",
                    opciones = null,
                    tipo = "abierta",
                    _id = "id_prueba"
                )
            )
        ),
        Survey(
            "2",
            "Segunda encuesta",
            "Encuesta de prueba para la aplicacion movil",
            "67391dd221e10b6cb5c7705f",
            listOf(
                Pregunta(
                    "Cual es tu color favorito",
                    opciones = listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Cual es tu color menos favorito",
                    opciones = listOf("Rojo", "Azul", "Amarillo", "Rosa"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Que nivel estudias",
                    opciones = listOf("Primaria", "Secundaria", "Preparatoria", "Licenciatura"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                ),
                Pregunta(
                    "Cual es tu materia favorita",
                    opciones = listOf("Español", "Matematicas", "Ingles", "Deportes"),
                    tipo = "opcion-multiple",
                    _id = "id_prueba"
                )
            )
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