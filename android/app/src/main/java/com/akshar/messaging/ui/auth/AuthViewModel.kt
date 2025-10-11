package com.akshar.messaging.ui.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akshar.messaging.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    
    private val authRepository = AuthRepository(application.applicationContext)
    
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        checkAuthentication()
    }

    private fun checkAuthentication() {
        viewModelScope.launch {
            _isLoading.value = true
            
            // Restore token if user is logged in
            if (authRepository.isLoggedIn()) {
                val token = authRepository.getToken()
                token?.let {
                    com.akshar.messaging.data.api.RetrofitClient.setToken(it)
                    android.util.Log.d("AuthViewModel", "Token restored on app start")
                }
            }
            
            _isAuthenticated.value = authRepository.isLoggedIn()
            _isLoading.value = false
        }
    }

    fun login(emailOrUsername: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.login(emailOrUsername, password)
            
            if (result.isSuccess) {
                _isAuthenticated.value = true
                _errorMessage.value = null
                
                // ✅ Connect socket AFTER token is saved with delay
                android.util.Log.d("AuthViewModel", "Login successful, connecting socket...")
                kotlinx.coroutines.delay(500)  // Wait for UI to settle
                com.akshar.messaging.data.socket.SocketManager.connect()
            } else {
                _isAuthenticated.value = false
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Login failed"
            }
            
            _isLoading.value = false
        }
    }
    
    fun register(username: String, email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = authRepository.register(username, email, password, firstName, lastName)
            
            if (result.isSuccess) {
                _isAuthenticated.value = true
                _errorMessage.value = null
                
                // ✅ Connect socket AFTER registration & token saved
                android.util.Log.d("AuthViewModel", "Registration successful, connecting socket...")
                com.akshar.messaging.data.socket.SocketManager.connect()
            } else {
                _isAuthenticated.value = false
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Registration failed"
            }
            
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true
            authRepository.logout()
            _isAuthenticated.value = false
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
}
