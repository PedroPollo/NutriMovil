package com.example.nutrimovil.features.home.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrimovil.R
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.features.addResearcher.ui.screens.AddResearcherActivity
import com.example.nutrimovil.features.downloadSurvey.ui.screens.DownloadSurveyActivity
import com.example.nutrimovil.features.downloadSurvey.viewmodels.DownloadSurveyViewModel
import com.example.nutrimovil.features.home.ui.components.Item
import com.example.nutrimovil.features.home.viewmodels.AplicatedSurveysViewModel
import com.example.nutrimovil.features.surveys.data.models.SurveyResponse
import com.example.nutrimovil.features.surveys.ui.screens.SurveysActivity
import com.example.nutrimovil.features.surveys.viewmodels.SurveyViewModel
import com.example.nutrimovil.features.uploadSurveys.ui.screens.UploadSurveysActivity
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import com.example.nutrimovil.ui.theme.PrimarioVar
import com.example.nutrimovil.ui.theme.SecundarioVar
import com.example.nutrimovil.viewmodels.LoginViewModel


class HomeActivity : ComponentActivity() {

    private val u = Us.getUser()
    private val loginViewModel: LoginViewModel by viewModels()
    private val surveysResponseViewModel: AplicatedSurveysViewModel by viewModels()
    private val downloadSurveyViewModel: DownloadSurveyViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        surveysResponseViewModel.createJSON(this,u!!.id)
        downloadSurveyViewModel.createJson(this, u.id)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Fondo
                ) {
                    loginViewModel.setUser()
                    surveysResponseViewModel.createJSON(this, u!!.id)
                    downloadSurveyViewModel.createJson(this, u.id)

                    if (!u.isAccepted) {
                        NotAccepted(this)
                    } else {
                        Accepted(
                            encuestador = loginViewModel.userG!!.id,
                            encuestas = surveysResponseViewModel.getSurveys(
                                u.id,
                                this
                            ),
                            context = this
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotAccepted(
    context: HomeActivity,
    loginViewModel: LoginViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 71.dp, bottom = 72.dp)
        ) {
            Image(
                modifier = Modifier.size(218.dp, 217.dp),
                painter = painterResource(id = R.drawable.logo2),
                contentDescription = "Not accepted by the investigator"
            )
        }
        Row(
            modifier = Modifier.padding(bottom = 22.dp)
        ) {
            Text(
                text = "Aun no has sido aceptado", fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
        }
        Row {
            Text(
                text = "El investigador que seleccionaste aun\n" + "  no te ha acreditado como su\n" + " encuestador,comunicate con tu \n" + "investigador para resolver este\n" + "inconveniente o registrate con otro\n" + "investigador",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
        Row(
            modifier = Modifier.padding(top = 72.dp)
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, AddResearcherActivity::class.java)
                    context.startActivity(intent)
                }, colors = ButtonDefaults.buttonColors(PrimarioVar)
            ) {
                Text(text = "Agregar Investigador", fontFamily = FontFamily.Monospace)
            }
        }
        Row(
            modifier = Modifier.padding(top = 15.dp)
        ) {
            Button(
                onClick = {
                    Us.closeUser()
                    context.finish()
                    loginViewModel.closeUser()
                }, colors = ButtonDefaults.buttonColors(SecundarioVar)
            ) {
                Text(text = "Cerrar sesion", fontFamily = FontFamily.Monospace)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Accepted(
    encuestas: List<SurveyResponse>?,
    context: HomeActivity,
    surveyViewModel: SurveyViewModel = viewModel(),
    encuestador: String,
    loginViewModel: LoginViewModel = viewModel()
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    rememberCoroutineScope()
    var showSurveySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val sheetSurveyState = rememberModalBottomSheetState()
    Scaffold(bottomBar = {
        BottomAppBar(containerColor = PrimarioVar, actions = {
            IconButton(onClick = { showBottomSheet = true }) {
                Icon(Icons.Filled.Menu, "Menu")
            }
        }, floatingActionButton = {
            FloatingActionButton(onClick = { showSurveySheet = true }) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Agregar encuesta"
                )
            }
        })
    }) {
        if (encuestas?.size != 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LazyColumn(
                    modifier = Modifier.padding(10.dp)
                ) {
                    encuestas?.forEachIndexed { _, surveyResponse ->
                        item {
                            Item(dia = surveyResponse.fecha, encuesta = surveyResponse.encuesta)
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier.padding(top = 96.dp, bottom = 9.dp)
                ) {
                    Image(
                        modifier = Modifier.size(323.dp, 227.dp),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo"
                    )
                }
                Row {
                    Text(
                        text = "No se han registrado encuestas",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Row(
                    modifier = Modifier.padding(top = 28.dp)
                ) {
                    Text(
                        text = "Para iniciar una encuesta preciona\n" + "el boton “+” ",
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            }
        }
        if (showSurveySheet) {
            ModalBottomSheet(
                onDismissRequest = { showSurveySheet = false },
                sheetState = sheetSurveyState,
                containerColor = SecundarioVar
            ) {
                Column(
                    modifier = Modifier
                        .padding(50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (survey in surveyViewModel.getSurveysName(encuestador, context)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(onClick = {
                                val intent = Intent(context, SurveysActivity::class.java)
                                intent.apply {
                                    putExtra("id", survey._id)
                                    putExtra("Encuestador", encuestador)
                                }
                                context.startActivity(intent)
                            }) {
                                Text(text = survey.nombre)
                            }
                        }
                    }
                }
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = SecundarioVar
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 50.dp, bottom = 50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.padding(5.dp), onClick = {
                            val intent = Intent(context, UploadSurveysActivity::class.java)
                            context.startActivity(intent)
                        }, colors = ButtonDefaults.buttonColors(PrimarioVar)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Cargar encuestas"
                        )
                        Text(text = "Cargar encuestas", fontFamily = FontFamily.Monospace)
                    }
                    Button(
                        modifier = Modifier.padding(5.dp),
                        onClick = {
                            val intent = Intent(context, DownloadSurveyActivity::class.java)
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(PrimarioVar)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Descargar encuestas"
                        )
                        Text(text = "Descargar encuestas", fontFamily = FontFamily.Monospace)
                    }
                    Button(
                        modifier = Modifier.padding(5.dp), onClick = {
                            val intent = Intent(context, AddResearcherActivity::class.java)
                            context.startActivity(intent)
                        }, colors = ButtonDefaults.buttonColors(PrimarioVar)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Default.Person,
                            contentDescription = "Investigador"
                        )
                        Text(text = "Agregar investigaor", fontFamily = FontFamily.Monospace)
                    }
                    Button(
                        modifier = Modifier.padding(5.dp), onClick = {
                            Us.closeUser()
                            context.finish()
                            loginViewModel.closeUser()
                        }, colors = ButtonDefaults.buttonColors(PrimarioVar)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 10.dp),
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Cerrar sesion"
                        )
                        Text(text = "Cerrar sesion", fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}