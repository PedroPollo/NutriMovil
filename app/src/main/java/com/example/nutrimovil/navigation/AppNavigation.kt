package com.example.nutrimovil.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nutrimovil.SplashScreen
import com.example.nutrimovil.data.models.SessionManager
import com.example.nutrimovil.features.uploadSurveys.ui.screens.AllView
import com.example.nutrimovil.features.uploadSurveys.ui.screens.BottomNavigationItem
import com.example.nutrimovil.features.uploadSurveys.ui.screens.DateView
import com.example.nutrimovil.features.uploadSurveys.ui.screens.PlaceView
import com.example.nutrimovil.features.uploadSurveys.ui.screens.UploadSurveysActivity
import com.example.nutrimovil.ui.screens.Login
import com.example.nutrimovil.ui.theme.PrimarioVar
import com.example.nutrimovil.ui.theme.SecundarioVar

@Composable
fun SplashNavigation(sessionManager: SessionManager) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route
    ) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(AppScreens.MainScreen.route) {
            Login(context = LocalContext.current,sessionManager = sessionManager)
        }
    }
}

    @Composable
    fun AppNavigation(
        context: UploadSurveysActivity
    ) {
        val navController = rememberNavController()

        val items = listOf(
            BottomNavigationItem(
                title = "Todo",
                selectedIcon = Icons.Filled.CheckCircle,
                unselectedIcon = Icons.Outlined.CheckCircle
            ),
            BottomNavigationItem(
                title = "Fecha",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange
            ),
            BottomNavigationItem(
                title = "Lugar",
                selectedIcon = Icons.Filled.LocationOn,
                unselectedIcon = Icons.Outlined.LocationOn
            )
        )
        var selectedItemIndex by rememberSaveable {
            mutableIntStateOf(0)
        }

        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = SecundarioVar
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                                navController.navigate(item.title)
                                //navController.navigate(item.title)
                            },
                            label = {
                                Text(text = item.title)
                            },
                            alwaysShowLabel = false,
                            icon = {
                                Icon(
                                    if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                    item.title
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(indicatorColor = PrimarioVar)
                        )
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "Todo",
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(items[0].title) {
                    AllView(context = context)
                }

                composable(items[1].title) {
                    DateView()
                }

                composable(items[2].title) {
                    PlaceView()
                }
            }
        }
    }