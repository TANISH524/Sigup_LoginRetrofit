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
fun SignupScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavHostController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    //  Error states
    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is ApiResult.Success) {
            navController.navigate("login")
        }
    }

    //  Validation function
    fun validate(): Boolean {
        var isValid = true

        if (name.trim().isEmpty()) {
            nameError = "enter the name!"
            isValid = false
        } else {
            nameError = ""
        }

        if (email.trim().isEmpty()) {
            emailError = "fill email!"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            emailError = "correct email!"
            isValid = false
        } else {
            emailError = ""
        }

        if (password.trim().isEmpty()) {
            passwordError = "Password required hai!"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "Password kam se kam 6 characters ka hona chahiye!"
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
        Text("Signup", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        //  Name field
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                if (it.trim().isNotEmpty()) nameError = ""
            },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = nameError.isNotEmpty(),
            supportingText = {
                if (nameError.isNotEmpty()) {
                    Text(text = nameError, color = MaterialTheme.colorScheme.error)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

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
                            viewModel.signup(name.trim(), email.trim(), password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Signup")
                }
            }
            else -> {
                Button(
                    onClick = {
                        // Pehle validate karo, tab hi API call karo
                        if (validate()) {
                            viewModel.signup(name.trim(), email.trim(), password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Signup")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("login") }) {
            Text("Back to Login")
        }
    }
}