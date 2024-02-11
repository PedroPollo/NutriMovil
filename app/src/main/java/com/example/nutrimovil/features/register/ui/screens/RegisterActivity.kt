package com.example.nutrimovil.features.register.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrimovil.R
import com.example.nutrimovil.features.addResearcher.viewmodels.ResearcherViewModel
import com.example.nutrimovil.io.RegisterData
import com.example.nutrimovil.ui.components.passwordField
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import com.example.nutrimovil.ui.theme.PrimarioVar
import com.example.nutrimovil.ui.theme.SecundarioVar

class RegisterActivity : ComponentActivity() {
    private val researcherViewModel: ResearcherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Fondo
                ) {
                    researcherViewModel.getInvestigadores()
                    Register(researcherViewModel, this)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Register(
    researcherViewModel: ResearcherViewModel,
    context: RegisterActivity
) {
    val list = researcherViewModel.investigadores
    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        var nombre by remember { mutableStateOf("") }
        var user by remember { mutableStateOf("") }
        var matricula by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        var repass by remember { mutableStateOf("") }
        var invId by remember { mutableStateOf<Int?>(null) }
        Row {
            Image(
                modifier = Modifier.size(92.dp, 101.dp),
                painter = painterResource(id = R.drawable.ca_uaz_237),
                contentDescription = "Logo"
            )
        }
        Box(
            Modifier.padding(top = 26.dp, bottom = 26.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text(text = "Nombre") },
                        textStyle = TextStyle(color = Color.Black)
                    )
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    OutlinedTextField(
                        value = user,
                        onValueChange = { user = it },
                        label = { Text(text = "Usuario") },
                        textStyle = TextStyle(color = Color.Black)
                    )
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    OutlinedTextField(
                        value = matricula,
                        onValueChange = { matricula = it },
                        label = { Text(text = "Matricula") },
                        textStyle = TextStyle(color = Color.Black)
                    )
                }
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    var isExpanded by remember {
                        mutableStateOf(false)
                    }
                    var selectedItem by remember {
                        mutableStateOf("")
                    }
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it }) {
                        TextField(
                            value = selectedItem,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false }
                        ) {
                            if (list != null) {
                                for (label in list) {
                                    DropdownMenuItem(text = { Text(text = label.nombre) }, onClick = {
                                        selectedItem = label.nombre
                                        isExpanded = false
                                        invId = label.id
                                        println()
                                    })
                                }
                            } else {
                                Toast.makeText(context, "Revise la conexion a internet", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    pass = passwordField()
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    repass = passwordField()
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Button(
                        onClick = {
                            if (pass == "" || repass == "" || nombre == "" || user == "" || matricula == "" || invId == null) {
                                Toast.makeText(context, "Un campo esta vacio", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (pass != repass) {
                                Toast.makeText(
                                    context,
                                    "Las contraseñas deben de coincidir",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                researcherViewModel.registerEncuestador(RegisterData(matricula = matricula,nombre = nombre, usuario = user, password = pass, investigadorId = invId!!), context)
                                context.finish()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(PrimarioVar)
                    ) {
                        Text(text = "Registrarme", fontFamily = FontFamily.Monospace)
                    }
                }
                Row {
                    TextButton(
                        onClick = {
                            context.finish()
                        }
                    ) {
                        Text(text = "Iniciar sesion", color = SecundarioVar)
                    }
                }
            }
        }
    }
}