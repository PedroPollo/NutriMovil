package com.example.nutrimovil.data.repository

import com.example.nutrimovil.data.models.User

class Us {
    companion object {
        private var user: User? = null

        fun setUser(user: User){
            this.user = user
        }

        fun closeUser(){
            this.user = null
        }

        fun getUser(): User? {
            return this.user
        }
    }
}