package com.example.sigupretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sigupretrofit.ui.theme.SigupRetrofitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SigupRetrofitTheme {
                val navController = rememberNavController()
                val viewModel: AuthViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "signup"
                ) {
                    // Signup
                    composable("signup") {
                        SignupScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    // Login
                    composable("login") {
                        LoginScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    // Home
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            navController = navController
                        )
                    }

                    // Detail/Edit
                    composable(
                        route = "detail/{id}/{name}/{email}/{password}",
                        arguments = listOf(
                            navArgument("id") { type = NavType.StringType },
                            navArgument("name") { type = NavType.StringType },
                            navArgument("email") { type = NavType.StringType },
                            navArgument("password") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        DetailScreen(
                            viewModel = viewModel,
                            navController = navController,
                            userId = backStackEntry.arguments?.getString("id") ?: "",
                            userName = backStackEntry.arguments?.getString("name") ?: "",
                            userEmail = backStackEntry.arguments?.getString("email") ?: "",
                            userPassword = backStackEntry.arguments?.getString("password") ?: ""
                        )
                    }
                }
            }
        }
    }
}