package com.akshar.messaging.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class LocalContact(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val phoneNumber: String?,
    val email: String?,
    val createdAt: Long = System.currentTimeMillis()
)

class ContactsManager(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "akshar_contacts",
        Context.MODE_PRIVATE
    )
    
    private val gson = Gson()
    
    fun saveContact(name: String, phoneNumber: String?, email: String?): LocalContact {
        val contacts = getAllContacts().toMutableList()
        
        val newContact = LocalContact(
            name = name,
            phoneNumber = phoneNumber?.takeIf { it.isNotBlank() },
            email = email?.takeIf { it.isNotBlank() }
        )
        
        contacts.add(newContact)
        
        val json = gson.toJson(contacts)
        prefs.edit().putString("contacts", json).apply()
        
        return newContact
    }
    
    fun getAllContacts(): List<LocalContact> {
        val json = prefs.getString("contacts", null) ?: return emptyList()
        
        val type = object : TypeToken<List<LocalContact>>() {}.type
        return gson.fromJson(json, type)
    }
    
    fun deleteContact(contactId: String) {
        val contacts = getAllContacts().toMutableList()
        contacts.removeAll { it.id == contactId }
        
        val json = gson.toJson(contacts)
        prefs.edit().putString("contacts", json).apply()
    }
    
    fun updateContact(contactId: String, name: String, phoneNumber: String?, email: String?): Boolean {
        val contacts = getAllContacts().toMutableList()
        val index = contacts.indexOfFirst { it.id == contactId }
        
        if (index == -1) return false
        
        contacts[index] = contacts[index].copy(
            name = name,
            phoneNumber = phoneNumber?.takeIf { it.isNotBlank() },
            email = email?.takeIf { it.isNotBlank() }
        )
        
        val json = gson.toJson(contacts)
        prefs.edit().putString("contacts", json).apply()
        
        return true
    }
    
    fun searchContacts(query: String): List<LocalContact> {
        if (query.isBlank()) return getAllContacts()
        
        return getAllContacts().filter { contact ->
            contact.name.contains(query, ignoreCase = true) ||
            contact.phoneNumber?.contains(query) == true ||
            contact.email?.contains(query, ignoreCase = true) == true
        }
    }
}

