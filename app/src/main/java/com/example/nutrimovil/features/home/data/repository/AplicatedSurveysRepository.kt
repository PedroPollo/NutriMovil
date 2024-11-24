package com.example.nutrimovil.features.home.data.repository

import android.content.Context
import com.example.nutrimovil.features.surveys.data.models.ListSurveyResponse
import com.example.nutrimovil.features.surveys.data.models.SurveyResponse
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

interface AplicatedSurveysRepository {
    fun getAplicatedSurveys(
        encuestador: String, context: Context
    ): MutableList<SurveyResponse>

    fun addAplicatedSurvey(aplicatedSurvey: String, context: Context, id: String)

    fun createJSON(context: Context, id: String)

    fun uploadJson(context: Context, id: String): String
}

const val ARCHIVO_NAME = "applicatedSurveys.json"

class AplicatedSurveysLocalRepository : AplicatedSurveysRepository {


    override fun getAplicatedSurveys(
        encuestador: String,
        context: Context
    ): MutableList<SurveyResponse> {
        val aplicatedSurveys =
            Gson().fromJson(leerJson(context, encuestador), ListSurveyResponse::class.java)
        val list = mutableListOf<SurveyResponse>()

        aplicatedSurveys.data.forEachIndexed { _, surveyResponse ->
            if (surveyResponse.encuestador == encuestador) list.add(surveyResponse)
        }
        return list
    }

    override fun addAplicatedSurvey(
        aplicatedSurvey: String,
        context: Context,
        id: String
    ) {
        guardarJson(context, aplicatedSurvey, id)
    }

    override fun createJSON(
        context: Context,
        id: String
    ) {
        val ruta = context.filesDir
        val archivo = File(ruta, id+ARCHIVO_NAME)
        if (!archivo.exists()) {
            val string = "{\n" + "  \"data\": [\n" + "  ]\n" + "}"
            archivo.writeText(string)
        }
    }

    override fun uploadJson(
        context: Context,
        id: String
    ): String {
        val content = leerJson(context, id)
        return content
    }

    fun borrarJson(
        context: Context,
        id: String
    ) {
        val ruta = context.filesDir
        val archivo = File(ruta, id+ARCHIVO_NAME)
        archivo.delete()
    }

    private fun leerJson(
        context: Context,
        id: String
    ): String {
        val ruta = context.filesDir
        val archivo = File(ruta, id+ARCHIVO_NAME)
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

    private fun guardarJson(context: Context, dataToWrite: String, id: String) {
        val string = leerJson(context = context, id)
        val jsonObject = JSONObject(string)
        jsonObject.getJSONArray("data").put(JSONObject(dataToWrite))
        val ruta = context.filesDir
        val archivo = File(ruta, id+ARCHIVO_NAME)
        archivo.writeText(jsonObject.toString())
    }
}