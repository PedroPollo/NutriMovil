package com.example.nutrimovil.features.addResearcher.ui.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.features.addResearcher.viewmodels.ResearcherViewModel
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import com.example.nutrimovil.ui.theme.PrimarioVar

class AddResearcherActivity : ComponentActivity() {
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
                    AddResearcher(
                        researcherViewModel, this
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddResearcher(
    researcherViewModel: ResearcherViewModel,
    context: AddResearcherActivity
) {
    val user = Us.getUser()
    val list = researcherViewModel.investigadores
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var invid: String? by remember {
            mutableStateOf(null)
        }
        Row(
            modifier = Modifier.padding(top = 44.dp, bottom = 589.dp)
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
                            DropdownMenuItem(text = { Text(text = label.nom_usuario + " " + label.apellidos_usuario) }, onClick = {
                                selectedItem = label.nom_usuario + "" + label.apellidos_usuario
                                isExpanded = false
                                invid = label._id
                            })
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "check your internet connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        Row {
            Button(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    if (invid == null || user == null){
                        Toast.makeText(context, "Can load this operation", Toast.LENGTH_LONG).show()
                    } else {
                        invid?.let { researcherViewModel.setInvestigador(user.id,
                            it.toString(), context) }
                        context.finish()
                    }
                },
                colors = ButtonDefaults.buttonColors(PrimarioVar)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 10.dp),
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Add"
                )
                Text(text = "Agregar investigador", fontFamily = FontFamily.Monospace)
            }
        }
    }
}