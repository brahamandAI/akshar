package com.akshar.messaging.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akshar.messaging.data.api.RetrofitClient
import com.akshar.messaging.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    
    private val apiService = RetrofitClient.apiService
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults: StateFlow<List<User>> = _searchResults.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    init {
        loadUserProfile()
    }
    
    fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val response = apiService.getProfile()
                if (response.isSuccessful && response.body() != null) {
                    _currentUser.value = response.body()!!.user
                } else {
                    _errorMessage.value = "Failed to load profile"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
            }
            
            _isLoading.value = false
        }
    }
    
    fun searchUsers(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val response = apiService.searchUsers(query)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    _searchResults.value = responseBody.data?.users ?: emptyList()
                } else {
                    _errorMessage.value = "Search failed"
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
                _searchResults.value = emptyList()
            }
            
            _isLoading.value = false
        }
    }
    
    fun clearSearch() {
        _searchResults.value = emptyList()
    }
}

