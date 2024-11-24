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

data class EncuestasContestadas(
    val data: List<EncuestaContestada>
)

data class EncuestaContestada(
    val id_encuestador: String,
    val fecha_aplicacion: String,
    val id_encuesta: String,
    val respuestas: List<PreguntaContestada>,
)

data class PreguntaContestada(
    val id_pregunta: String,
    val respuesta: String
)

data class EncuestasData(
    val investigadores: List<String?>
)

data class RegisterData(
    val matricula: String,
    val nombre: String,
    val username: String,
    val password: String,
    val investigadorId: String
)

data class InvHasEnc(
    val encId: String,
    val invId: String
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

    @POST(value = "encuestas")
    fun getEncuestas(
        @Body body: EncuestasData
    ):
            Call<EncuestasResponse>

   @POST(value = "encuestas/loadresp")
   fun uploadEncuestas(
       @Body body: EncuestasContestadas
   ):
           Call<ShowableResponse>

    companion object Factory {
        private const val BASE_URL = "http://148.204.142.3:3002/api/"
        fun create():APIService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(APIService::class.java)
        }
    }
}