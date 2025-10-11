package com.akshar.messaging.utils

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREF_NAME = "AksharAuth"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USERNAME = "username"
    private const val KEY_EMAIL = "email"
    private const val KEY_FULL_NAME = "full_name"
    
    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    
    fun saveToken(context: Context, token: String) {
        getPreferences(context).edit().putString(KEY_TOKEN, token).apply()
    }
    
    fun getToken(context: Context): String? {
        return getPreferences(context).getString(KEY_TOKEN, null)
    }
    
    fun getBearerToken(context: Context): String? {
        val token = getToken(context)
        return if (token != null) "Bearer $token" else null
    }
    
    fun saveUserInfo(context: Context, userId: String, username: String, email: String, fullName: String) {
        getPreferences(context).edit().apply {
            putString(KEY_USER_ID, userId)
            putString(KEY_USERNAME, username)
            putString(KEY_EMAIL, email)
            putString(KEY_FULL_NAME, fullName)
            apply()
        }
    }
    
    fun getUserId(context: Context): String? {
        return getPreferences(context).getString(KEY_USER_ID, null)
    }
    
    fun getUsername(context: Context): String? {
        return getPreferences(context).getString(KEY_USERNAME, null)
    }
    
    fun getEmail(context: Context): String? {
        return getPreferences(context).getString(KEY_EMAIL, null)
    }
    
    fun getFullName(context: Context): String? {
        return getPreferences(context).getString(KEY_FULL_NAME, null)
    }
    
    fun isLoggedIn(context: Context): Boolean {
        return getToken(context) != null
    }
    
    fun logout(context: Context) {
        getPreferences(context).edit().clear().apply()
    }
}

