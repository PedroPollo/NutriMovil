package com.example.nutrimovil.features.addResearcher.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrimovil.io.APIService
import com.example.nutrimovil.io.InvHasEnc
import com.example.nutrimovil.io.RegisterData
import com.example.nutrimovil.io.response.Investigador
import com.example.nutrimovil.io.response.InvestigadoresResponse
import com.example.nutrimovil.io.response.ShowableResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ResearcherViewModel : ViewModel() {
    private val apiService: APIService by lazy {
        APIService.create()
    }
    var investigadores: List<Investigador>? = null

    fun getInvestigadores() {
        viewModelScope.launch {
            try {
                val response = apiService.getInvestigadores()
                response.enqueue(object : Callback<InvestigadoresResponse> {
                    override fun onResponse(
                        call: Call<InvestigadoresResponse>,
                        response: Response<InvestigadoresResponse>
                    ) {
                        if (response.isSuccessful) {
                            val investigadoresResponse = response.body()
                            investigadores = investigadoresResponse?.body
                        }
                    }

                    override fun onFailure(call: Call<InvestigadoresResponse>, t: Throwable) {
                        Log.d("Servidor", "No se pudo conectar", t)
                    }

                })
            } catch (e: IOException) {
                Log.e("ViewModel", "Error", e)
            }
        }
    }

    fun setInvestigador(enc: Int, inv: Int, context: Context) {
        viewModelScope.launch {
            try {
                val response = apiService.setInvestigador(InvHasEnc(enc, inv))
                response.enqueue(object : Callback<ShowableResponse> {
                    override fun onResponse(
                        call: Call<ShowableResponse>,
                        response: Response<ShowableResponse>
                    ) {
                        if (response.isSuccessful) {
                            val showableResponse = response.body()
                            if (showableResponse != null) {
                                if (showableResponse.error) {
                                    Toast.makeText(
                                        context,
                                        showableResponse.body,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Se agrego investigador",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ShowableResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            } catch (e: IOException) {
                Log.d("ViewModel", "Fallo en el viewmodel", e)
            }
        }
    }

    fun registerEncuestador(data: RegisterData, context: Context) {
        viewModelScope.launch {
            try {
                val response = apiService.postRegister(data)
                response.enqueue(object : Callback<ShowableResponse> {
                    override fun onResponse(
                        call: Call<ShowableResponse>,
                        response: Response<ShowableResponse>
                    ) {
                        if (response.isSuccessful) {
                            val showableResponse = response.body()
                            if (showableResponse != null) {
                                if (showableResponse.error) {
                                    Toast.makeText(
                                        context,
                                        "Register task failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Se registro exitosamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ShowableResponse>, t: Throwable) {
                        Log.d("Servidor", "No se pudo conectar", t)
                    }

                })
            } catch (e: IOException) {
                Log.e("ViewModel", "Error", e)
            }
        }
    }
}