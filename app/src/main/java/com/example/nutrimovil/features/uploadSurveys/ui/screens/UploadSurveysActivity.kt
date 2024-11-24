@file:Suppress("NAME_SHADOWING")

package com.example.nutrimovil.features.uploadSurveys.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.features.home.ui.screens.HomeActivity
import com.example.nutrimovil.features.home.viewmodels.AplicatedSurveysViewModel
import com.example.nutrimovil.navigation.AppNavigation
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme

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
                    AppNavigation(context = this)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AllView(
    context: UploadSurveysActivity,
    applicatedSurveysViewModel: AplicatedSurveysViewModel = viewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            val intent = Intent(context,HomeActivity::class.java)
            applicatedSurveysViewModel.uploadSurveys(context, Us.getUser()!!.id)
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