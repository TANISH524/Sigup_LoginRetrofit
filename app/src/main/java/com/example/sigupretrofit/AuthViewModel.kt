package com.example.sigupretrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow<ApiResult<String>?>(null)
    val authState: StateFlow<ApiResult<String>?> = _authState

    private val _usersState = MutableStateFlow<ApiResult<List<LoginResponse>>>(ApiResult.Loading)
    val usersState: StateFlow<ApiResult<List<LoginResponse>>> = _usersState

    private val _isLoginSuccess = MutableStateFlow(false)
    val isLoginSuccess: StateFlow<Boolean> = _isLoginSuccess

    fun resetAuthState() {
        _authState.value = null
        _isLoginSuccess.value = false
    }

    // ─── Signup ──────────────────────────
    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = ApiResult.Loading
            try {
                //  Saare users lo aur email check karo
                val allUsers = RetrofitInstance.api.getAllUsers()
                val exists = allUsers.find {
                    it.email.trim().lowercase() == email.trim().lowercase()
                }
                if (exists != null) {
                    _authState.value = ApiResult.Error("Already registered with this email!")
                    return@launch
                }
                //  Server pe save karo
                RetrofitInstance.api.signup(SignupRequest(name, email, password))
                _authState.value = ApiResult.Success("Signup done!")
            } catch (e: Exception) {
                _authState.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    // ─── Login ───────────────────────────
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = ApiResult.Loading
            _isLoginSuccess.value = false
            try {
                //  Saare users lo server se
                val allUsers = RetrofitInstance.api.getAllUsers()

                //  Email dhundo — proper verify
                val user = allUsers.find {
                    it.email.trim().lowercase() == email.trim().lowercase()
                }

                if (user == null) {
                    _authState.value = ApiResult.Error("Email not found!")
                    return@launch
                }

                // Password verify
                if (user.password == password) {
                    _isLoginSuccess.value = true
                    _authState.value = ApiResult.Success("Welcome ${user.name}!")
                } else {
                    _authState.value = ApiResult.Error("Password wrong!")
                }

            } catch (e: Exception) {
                _authState.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    // ─── Get All Users ───────────────────
    fun getAllUsers() {
        viewModelScope.launch {
            _usersState.value = ApiResult.Loading
            try {
                val users = RetrofitInstance.api.getAllUsers()
                _usersState.value = ApiResult.Success(users)
            } catch (e: Exception) {
                _usersState.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    // ─── Update User ─────────────────────
    fun updateUser(id: String, name: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = ApiResult.Loading
            try {
                RetrofitInstance.api.updateUser(id, SignupRequest(name, email, password))
                _authState.value = ApiResult.Success("Update done!")
                getAllUsers()
            } catch (e: Exception) {
                _authState.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    // ─── Delete User ─────────────────────
    fun deleteUser(id: String) {
        viewModelScope.launch {
            _authState.value = ApiResult.Loading
            try {
                RetrofitInstance.api.deleteUser(id)
                _authState.value = ApiResult.Success("Deleted!")
                getAllUsers()
            } catch (e: Exception) {
                _authState.value = ApiResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}