package com.example.nutrimovil.data.models

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson

object SessionManager {

    private const val PREFS_NAME = "UserSessionPrefs"
    private const val KEY_USER = "user"

    fun saveUserSession(user: User, context: Context) {
        val userJson = Gson().toJson(user)
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            putString(KEY_USER, userJson)
        }
    }

    fun getUserSession(context: Context): User? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val userJson = prefs.getString(KEY_USER, null)
        return if (userJson != null) {
            Gson().fromJson(userJson, User::class.java)
        } else {
            null
        }
    }

    fun clearUserSession(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit {
            clear()
        }
    }
}