package com.example.sigupretrofit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavHostController
) {
    val usersState by viewModel.usersState.collectAsState()

    LaunchedEffect(Unit) { viewModel.getAllUsers() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("All Users", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        when (val state = usersState) {
            is ApiResult.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ApiResult.Error -> {
                Text(text = state.message, color = MaterialTheme.colorScheme.error)
            }
            is ApiResult.Success -> {
                LazyColumn {
                    items(state.data) { user ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    navController.navigate(
                                        "detail/${user.id}/${user.name}/${user.email}/${user.password}"
                                    )
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(user.name,
                                        style = MaterialTheme.typography.titleMedium)
                                    Text(user.email,
                                        style = MaterialTheme.typography.bodyMedium)
                                }
                                IconButton(onClick = { viewModel.deleteUser(user.id) }) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

