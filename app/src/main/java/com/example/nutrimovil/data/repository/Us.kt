package com.example.nutrimovil.data.repository

import android.content.Context
import com.example.nutrimovil.data.models.User

class Us {
    companion object {
        private var user: User? = null

        fun setUser(user: User,context: Context){
            this.user = user
        }

        fun closeUser(context: Context){
            this.user = null
        }

        fun getUser(context: Context): User? {
            return this.user
        }
    }
}