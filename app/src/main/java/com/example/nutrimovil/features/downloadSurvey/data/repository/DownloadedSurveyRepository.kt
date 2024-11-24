package com.example.nutrimovil.features.downloadSurvey.data.repository

import android.content.Context
import com.example.nutrimovil.features.surveys.data.models.ListSurvey
import com.example.nutrimovil.features.surveys.data.models.Survey
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

interface DownloadedSurveyRepository {
    fun getDownloadedSurveys(
        encuestador: String, context: Context
    ): MutableList<Survey>

    fun downloadSurvey(
        survey: Survey, context: Context, id: String
    )

    fun createJSON(
        context: Context, id: String
    )
}

const val ARCHIVO_NAME = "downloadedsurveys.json"

class DownloadedSurveysLocalRepository: DownloadedSurveyRepository {
    override fun getDownloadedSurveys(encuestador: String, context: Context): MutableList<Survey> {
        val surveys = Gson().fromJson(leerJson(context, encuestador), ListSurvey::class.java)
        val list = mutableListOf<Survey>()

        surveys.data.forEachIndexed {_, survey ->
            list.add(survey)
        }
        return list
    }

    override fun downloadSurvey(survey: Survey, context: Context, id: String) {
        guardarJson(context, survey, id)
    }

    override fun createJSON(context: Context, id: String) {
        val ruta = context.filesDir
        val archivo = File(ruta, id+ ARCHIVO_NAME)
        if (!archivo.exists()) {
            val string = "{{\\n\" + \"  \\\"data\\\": [\\n\" + \"  ]\\n\" + \"}}"
            archivo.writeText(string)
        }
    }

    private fun leerJson(
        context: Context,
        id: String
    ): String {
        val ruta = context.filesDir
        val archivo = File(ruta,id+ ARCHIVO_NAME)
        val leeArchivo = FileInputStream(archivo)
        try {
            val isr = InputStreamReader(leeArchivo)
            val bufferedReader = BufferedReader(isr)

            val stringBuilder = StringBuilder()
            var line: String?

            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line).append('\n')
            }
            bufferedReader.close()
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
    }

    private fun guardarJson(context: Context, dataToWrite: Survey, id:String) {
        // Lee el contenido del archivo JSON existente
        val string = leerJson(context, id)
        val jsonObject = JSONObject(string)

        // Convierte `dataToWrite` en un JSONObject
        val surveyJson = JSONObject().apply {
            put("_id", dataToWrite._id)
            put("nombre", dataToWrite.nombre)
            put("descripcion", dataToWrite.descripcion)
            put("idInvestigador", dataToWrite.autor)

            // Convierte la lista de preguntas en un JSONArray
            val preguntasArray = JSONArray()
            dataToWrite.preguntas.forEach { question ->
                val questionJson = JSONObject().apply {
                    put("preguntaId", question._id)
                    put("texto", question.texto)
                    // Añade otros campos de `Question` según sea necesario
                }
                preguntasArray.put(questionJson)
            }
            put("preguntas", preguntasArray)
        }

        // Agrega el nuevo objeto de la encuesta al array `data`
        val ruta = context.filesDir
        jsonObject.getJSONArray("data").put(surveyJson)
        val archivo = File(ruta, id+ ARCHIVO_NAME)
        archivo.writeText(jsonObject.toString())
    }
}