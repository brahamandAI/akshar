package com.akshar.messaging.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.akshar.messaging.data.api.RetrofitClient
import com.akshar.messaging.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(context: Context) {
    
    private val apiService = RetrofitClient.apiService
    private val prefs: SharedPreferences = context.getSharedPreferences("akshar_prefs", Context.MODE_PRIVATE)
    
    suspend fun register(
        username: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<AuthData> = withContext(Dispatchers.IO) {
        try {
            val request = RegisterRequest(username, email, password, firstName, lastName)
            val response = apiService.register(request)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()?.data
                authData?.let {
                    saveToken(it.token)
                    saveUser(it.user)
                    RetrofitClient.setToken(it.token)
                }
                Result.success(authData!!)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun login(emailOrUsername: String, password: String): Result<AuthData> = withContext(Dispatchers.IO) {
        try {
            val request = LoginRequest(emailOrUsername, password)
            val response = apiService.login(request)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val authData = response.body()?.data
                authData?.let {
                    saveToken(it.token)
                    saveUser(it.user)
                    RetrofitClient.setToken(it.token)
                }
                Result.success(authData!!)
            } else {
                Result.failure(Exception(response.body()?.message ?: "Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun logout(): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.logout()
            clearToken()
            clearUser()
            RetrofitClient.setToken(null)
            Result.success(true)
        } catch (e: Exception) {
            clearToken()
            clearUser()
            RetrofitClient.setToken(null)
            Result.success(true)
        }
    }
    
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
    
    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }
    
    fun getUser(): User? {
        val id = prefs.getString("user_id", null) ?: return null
        val username = prefs.getString("user_username", null) ?: return null
        val email = prefs.getString("user_email", null) ?: return null
        val firstName = prefs.getString("user_first_name", null) ?: return null
        val lastName = prefs.getString("user_last_name", null) ?: return null
        val avatar = prefs.getString("user_avatar", null)
        val status = prefs.getString("user_status", "offline") ?: "offline"
        
        return User(id, username, email, firstName, lastName, avatar, status)
    }
    
    private fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
        RetrofitClient.setToken(token)
    }
    
    private fun saveUser(user: User) {
        prefs.edit().apply {
            putString("user_id", user.id)
            putString("user_username", user.username)
            putString("user_email", user.email)
            putString("user_first_name", user.firstName)
            putString("user_last_name", user.lastName)
            putString("user_avatar", user.avatar)
            putString("user_status", user.status)
            apply()
        }
    }
    
    private fun clearToken() {
        prefs.edit().remove("auth_token").apply()
    }
    
    private fun clearUser() {
        prefs.edit().apply {
            remove("user_id")
            remove("user_username")
            remove("user_email")
            remove("user_first_name")
            remove("user_last_name")
            remove("user_avatar")
            remove("user_status")
            apply()
        }
    }
}

