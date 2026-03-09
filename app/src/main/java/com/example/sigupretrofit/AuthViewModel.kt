package com.example.sigupretrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sigupretrofit.RetrofitInstance.api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _result = MutableStateFlow<String?>(null)
    val result: StateFlow<String?> = _result

    // Result reset karo
    fun resetResult() {
        _result.value = null
    }

    // ─── Signup ──────────────────────────
    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _result.value = null
            try {
                val existingUsers = api.login(email)   // Prevent the same id login
                if (existingUsers.isNotEmpty()) {
                    _result.value = " Already RegisteredWith this email"
                    return@launch  // ← Ruk jao!
                }

                val response = RetrofitInstance.api.signup(
                    SignupRequest(name, email, password)
                )
                android.util.Log.d("AUTH", "Signup: ${response.id}")
                _result.value = " Signup !"
            } catch (e: Exception) {
                android.util.Log.e("AUTH", "Error: ${e.message}")
                _result.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ─── Login ───────────────────────────
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _result.value = null
            try {
                val users = RetrofitInstance.api.login(email)
                if (users.isEmpty()) {
                    _result.value = " Email not registered!"
                } else {
                    val user = users[0]
                    if (user.password == password) {
                        android.util.Log.d("AUTH", "Login: ${user.name}")
                        _result.value = " Login successful Welcome ${user.name}!"
                    } else {
                        _result.value = "Password wronng"
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("AUTH", "Error: ${e.message}")
                _result.value = "${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
