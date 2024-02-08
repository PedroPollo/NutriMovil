@file:Suppress("NAME_SHADOWING")

package com.example.nutrimovil.features.uploadSurveys.ui.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.features.home.ui.screens.HomeActivity
import com.example.nutrimovil.features.home.viewmodels.AplicatedSurveysViewModel
import com.example.nutrimovil.navigation.AppNavigation
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import java.util.Calendar
import java.util.Date

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class UploadSurveysActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMovilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Fondo
                ) {
                    AppNavigation(this)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AllView(
    context: UploadSurveysActivity,
    applicatedSurveysViewModel: AplicatedSurveysViewModel = viewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val intent = Intent(context,HomeActivity::class.java)
            applicatedSurveysViewModel.uploadJSON(context, Us.getUser()!!.id)
            context.startActivity(intent)
            context.finish()
        }) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowUp,
                contentDescription = "Subir",
                modifier = Modifier.padding(end = 15.dp)
            )
            Text(text = "Subir")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DateView() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val year: Int
        val month: Int
        val day: Int

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.time = Date()

        val date = remember { mutableStateOf("") }
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayMonth: Int ->
                date.value = "$dayMonth/$month/$year"
            }, year, month, day
        )

        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Elegir Fecha")
        }
        Text(text = "Fecha seleccionada: ${date.value}", modifier = Modifier.padding(15.dp))
        Button(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowUp,
                contentDescription = "Subir",
                modifier = Modifier.padding(end = 15.dp)
            )
            Text(text = "Subir")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var place by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = place,
            onValueChange = { place = it }
        )
        Button(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowUp,
                contentDescription = "Subir",
                modifier = Modifier.padding(end = 15.dp)
            )
            Text(text = "Subir")
        }
    }
}