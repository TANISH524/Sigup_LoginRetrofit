package com.example.sigupretrofit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //  Error states
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()
    val isLoginSuccess by viewModel.isLoginSuccess.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.resetAuthState()
    }

    LaunchedEffect(isLoginSuccess) {
        if (isLoginSuccess) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // Validation function
    fun validate(): Boolean {
        var isValid = true

        if (email.trim().isEmpty()) {
            emailError = "fill email!"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            emailError = "fill correct email!"
            isValid = false
        } else {
            emailError = ""
        }

        if (password.trim().isEmpty()) {
            passwordError = "fill password!"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "at least 6 char password"
            isValid = false
        } else {
            passwordError = ""
        }

        return isValid
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        //  Email field
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (it.trim().isNotEmpty()) emailError = ""
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError.isNotEmpty(),
            supportingText = {
                if (emailError.isNotEmpty()) {
                    Text(text = emailError, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        //  Password field
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (it.length >= 6) passwordError = ""
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.isNotEmpty(),
            supportingText = {
                if (passwordError.isNotEmpty()) {
                    Text(text = passwordError, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(24.dp))

        when (authState) {
            is ApiResult.Loading -> CircularProgressIndicator()
            is ApiResult.Error -> {
                Text(
                    text = (authState as ApiResult.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (validate()) {
                            viewModel.login(email.trim(), password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
            else -> {
                Button(
                    onClick = {
                        //  Pehle validate karo, tab hi API call karo
                        if (validate()) {
                            viewModel.login(email.trim(), password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
            }
        }
    }
}
