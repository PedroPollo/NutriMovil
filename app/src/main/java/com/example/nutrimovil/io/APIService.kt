package com.example.nutrimovil.io

import com.example.nutrimovil.io.response.EncuestasResponse
import com.example.nutrimovil.io.response.InvestigadoresResponse
import com.example.nutrimovil.io.response.LoginResponse
import com.example.nutrimovil.io.response.ShowableResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginData(
    val usuario: String,
    val password: String
)

data class RegisterData(
    val matricula: String,
    val nombre: String,
    val usuario: String,
    val password: String,
    val investigadorId: Int
)

data class InvHasEnc(
    val enc_id: String,
    val inv_id: String
)

data class IdInvestigador(
    val inv_id: String
)

interface APIService {
    @POST(value = "auth/login")
    fun postLogin(
        @Body body: LoginData
    ):
            Call<LoginResponse>

    @GET(value = "inv")
    fun getInvestigadores():
            Call<InvestigadoresResponse>

    @POST(value = "enc/register")
    fun postRegister(
        @Body body: RegisterData
    ):
            Call<ShowableResponse>

    @POST(value = "enc/agregarInv")
    fun setInvestigador(
        @Body body: InvHasEnc
    ):
            Call<ShowableResponse>

    /*@POST(value = "inv/encuestas")
    fun getEncuestas(
        @Body body: IdInvestigador
    ):
            Call<ShowableResponse>*/

    @GET("inv/encuestas")
    fun getEncuestas():
            Call<EncuestasResponse>

    companion object Factory {
        private const val BASE_URL = "https://nutribackend.onrender.com/api/"
        fun create():APIService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}