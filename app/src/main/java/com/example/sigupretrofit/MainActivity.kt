package com.example.sigupretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sigupretrofit.ui.theme.SigupRetrofitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SigupRetrofitTheme {
                NavigationRes()  // ← Yahan call karo
            }
        }
    }
}

@Composable
fun NavigationRes() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "signup"
    ) {
        composable("signup") {
            SignupScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                navController=navController
            )
        }
    }
}