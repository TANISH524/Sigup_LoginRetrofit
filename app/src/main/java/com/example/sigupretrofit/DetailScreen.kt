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
fun DetailScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavHostController,
    userId: String,
    userName: String,
    userEmail: String,
    userPassword: String
) {
    var name by remember { mutableStateOf(userName) }
    var email by remember { mutableStateOf(userEmail) }
    var password by remember { mutableStateOf(userPassword) }

    val authState by viewModel.authState.collectAsState()

    var isReady by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.resetAuthState()
        isReady = true
    }

    //  Sirf update ke baad wapas jao
    LaunchedEffect(authState) {
        if (isReady && authState is ApiResult.Success) {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(" Edit User", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
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
                    onClick = { viewModel.updateUser(userId, name, email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update")
                }
            }
            else -> {
                Button(
                    onClick = { viewModel.updateUser(userId, name, email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Update")
                }
            }
        }
    }
}