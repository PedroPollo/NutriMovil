package com.example.nutrimovil.features.downloadSurvey.data.repository

import android.content.Context
import android.widget.Toast
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

    fun applySurvey(
        nombre: String,
        encuestador: String,
        context: Context
    ): Survey
}

const val ARCHIVO_NAME = "downloadedsurveys.json"

class DownloadedSurveysLocalRepository : DownloadedSurveyRepository {

    override fun getDownloadedSurveys(encuestador: String, context: Context): MutableList<Survey> {
        val surveys = Gson().fromJson(leerJson(context, encuestador), ListSurvey::class.java)
        val list = mutableListOf<Survey>()

        surveys.data.forEachIndexed { _, survey ->
            list.add(survey)
        }
        return list
    }

    override fun downloadSurvey(survey: Survey, context: Context, id: String) {
        // Verificar si la encuesta ya está descargada
        if (isSurveyDownloaded(survey._id, context, id)) {
            // Mostrar un Toast indicando que la encuesta ya está descargada
            Toast.makeText(context, "La encuesta ya está descargada.", Toast.LENGTH_SHORT).show()
        } else {
            // Si no está descargada, proceder con el guardado
            guardarJson(context, survey, id)
            Toast.makeText(context, "Encuesta descargada correctamente.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun createJSON(context: Context, id: String) {
        val ruta = context.filesDir
        val archivo = File(ruta, id + ARCHIVO_NAME)
        if (!archivo.exists()) {
            val string = "{\n" + "  \"data\": [\n" + "  ]\n" + "}"
            archivo.writeText(string)
        }
    }

    override fun applySurvey(id: String, encuestador: String, context: Context): Survey {
        val encuestas = getDownloadedSurveys(encuestador, context)
        return encuestas.find { it._id == id }!!.apply {
            // Ajusta las opciones de las preguntas al descargar la encuesta
            preguntas.forEach { pregunta ->
                if (pregunta.opciones?.isEmpty() == true) {
                    pregunta.opciones = null
                }
            }
        }
    }

    private fun leerJson(context: Context, id: String): String {
        val ruta = context.filesDir
        val archivo = File(ruta, id + ARCHIVO_NAME)
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

    private fun guardarJson(context: Context, dataToWrite: Survey, id: String) {
        // Lee el contenido del archivo JSON existente
        val string = leerJson(context, id)
        val jsonObject = JSONObject(string)

        // Convierte `dataToWrite` en un JSONObject
        val surveyJson = JSONObject().apply {
            put("_id", dataToWrite._id)
            put("nombre", dataToWrite.nombre)
            put("descripcion", dataToWrite.descripcion)
            put("autor", dataToWrite.autor)

            // Convierte la lista de preguntas en un JSONArray
            val preguntasArray = JSONArray()
            dataToWrite.preguntas.forEach { question ->
                val questionJson = JSONObject().apply {
                    put("_id", question._id)
                    put("texto", question.texto)
                    put("tipo", question.tipo)
                    // Añade otros campos de `Question` según sea necesario
                    question.opciones?.let { opciones ->
                        val opcionesArray = JSONArray()
                        opciones.forEach { opcion ->
                            opcionesArray.put(opcion)
                        }
                        put("opciones", opcionesArray)
                    }
                }
                preguntasArray.put(questionJson)
            }
            put("preguntas", preguntasArray)
        }

        // Agrega el nuevo objeto de la encuesta al array `data`
        val ruta = context.filesDir
        jsonObject.getJSONArray("data").put(surveyJson)
        val archivo = File(ruta, id + ARCHIVO_NAME)
        archivo.writeText(jsonObject.toString())
    }

    /**
     * Verifica si una encuesta ya está descargada.
     * @param surveyId El ID de la encuesta a verificar.
     * @param context Contexto de la aplicación.
     * @param id Identificador del archivo JSON.
     * @return `true` si la encuesta ya está descargada, de lo contrario `false`.
     */
    private fun isSurveyDownloaded(surveyId: String, context: Context, id: String): Boolean {
        val surveys = getDownloadedSurveys(id, context)
        return surveys.any { it._id == surveyId }
    }
}