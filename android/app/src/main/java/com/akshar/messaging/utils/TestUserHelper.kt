package com.akshar.messaging.utils

import com.akshar.messaging.data.local.LocalContact

object TestUserHelper {
    
    // Test users created in backend with their phone numbers and IDs
    private val testUsers = mapOf(
        "9812237388" to "68e88553637c6233a641221c", // gurav (UPDATED from DB)
        "9876543210" to "68e8855d637c6233a6412220", // alice (UPDATED from DB)
        "9123456789" to "68e88565637c6233a6412224", // bob (UPDATED from DB)
        "9234567890" to "68e8856d637c6233a6412228"  // charlie (UPDATED from DB)
    )
    
    /**
     * Check if a phone number belongs to a test user
     */
    fun isTestUser(phoneNumber: String): Boolean {
        return testUsers.containsKey(phoneNumber)
    }
    
    /**
     * Get the user ID for a test user phone number
     */
    fun getTestUserId(phoneNumber: String): String? {
        return testUsers[phoneNumber]
    }
    
    /**
     * Get all test user phone numbers
     */
    fun getAllTestUserPhones(): List<String> {
        return testUsers.keys.toList()
    }
    
    /**
     * Get test user info for display
     */
    fun getTestUserInfo(): Map<String, String> {
        return mapOf(
            "9812237388" to "Gurav (gurav)",
            "9876543210" to "Alice (alice)", 
            "9123456789" to "Bob (bob)",
            "9234567890" to "Charlie (charlie)"
        )
    }
    
    /**
     * Create test contacts with proper user IDs
     */
    fun createTestContacts(): List<LocalContact> {
        return listOf(
            LocalContact(
                id = System.currentTimeMillis().toString(),
                name = "Gurav",
                phoneNumber = "9812237388",
                email = "gurav@test.com"
            ),
            LocalContact(
                id = (System.currentTimeMillis() + 1).toString(),
                name = "Alice", 
                phoneNumber = "9876543210",
                email = "alice@test.com"
            ),
            LocalContact(
                id = (System.currentTimeMillis() + 2).toString(),
                name = "Bob",
                phoneNumber = "9123456789", 
                email = "bob@test.com"
            ),
            LocalContact(
                id = (System.currentTimeMillis() + 3).toString(),
                name = "Charlie",
                phoneNumber = "9234567890",
                email = "charlie@test.com"
            )
        )
    }
}
