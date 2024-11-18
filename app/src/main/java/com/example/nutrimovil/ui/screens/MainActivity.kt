package com.example.nutrimovil.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nutrimovil.R
import com.example.nutrimovil.data.models.SessionManager
import com.example.nutrimovil.data.repository.Us
import com.example.nutrimovil.features.home.ui.screens.HomeActivity
import com.example.nutrimovil.features.register.ui.screens.RegisterActivity
import com.example.nutrimovil.navigation.SplashNavigation
import com.example.nutrimovil.ui.components.passwordField
import com.example.nutrimovil.ui.theme.Fondo
import com.example.nutrimovil.ui.theme.NutriMovilTheme
import com.example.nutrimovil.ui.theme.PrimarioVar
import com.example.nutrimovil.ui.theme.SecundarioVar
import com.example.nutrimovil.viewmodels.LoginViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val sessionManager: SessionManager by inject()

    override fun onRestart() {
        super.onRestart()
        sessionManager.clearUserSession(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriMovilTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Fondo
                ) {
                    SplashNavigation(sessionManager)
                    if (sessionManager.getUserSession(this) != null){
                        Us.setUser(sessionManager.getUserSession(this)!!)
                        val intent = Intent(this, HomeActivity::class.java)
                        this.startActivity(intent)
                    }
                }
            }
        }
    }
}

@SuppressLint("ShowToast")
@Composable
fun Login(
    context: Context,
    loginViewModel: LoginViewModel = viewModel(),
    sessionManager: SessionManager
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        var user by remember { mutableStateOf("") }
        var pass by remember { mutableStateOf("") }
        Row {
            Image(
                modifier = Modifier.size(184.dp, 202.dp),
                painter = painterResource(id = R.drawable.ca_uaz_237),
                contentDescription = "Logo"
            )
        }
        Box(
            Modifier.padding(top = 100.dp, bottom = 100.dp)
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
                        value = user,
                        onValueChange = { user = it },
                        label = { Text(text = "Usuario") },
                        textStyle = TextStyle(color = Color.Black)
                    )
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    pass = passwordField()
                }
                Row(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Button(
                        onClick = {
                            loginViewModel.login(user, pass)
                        },
                        colors = ButtonDefaults.buttonColors(PrimarioVar)
                    ) {
                        Text(text = "Iniciar Sesion", fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
        Row {
            TextButton(
                onClick = {
                    context.startActivity(Intent(context, RegisterActivity::class.java))
                }
            ) {
                Text(text = "Registrarme", color = SecundarioVar)
            }
        }
        if (loginViewModel.success.value) {
            Toast.makeText(context, "Login Correcto", Toast.LENGTH_SHORT).show()
            loginViewModel.success.value = false
            Us.setUser(loginViewModel.userG!!)
            user = ""
            Us.getUser()?.let { sessionManager.saveUserSession(it, context) }
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }
}