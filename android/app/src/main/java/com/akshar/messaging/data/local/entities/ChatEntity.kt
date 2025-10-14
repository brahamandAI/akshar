package com.akshar.messaging.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.akshar.messaging.data.local.converters.DateConverter
import com.akshar.messaging.data.local.converters.ListConverter
import java.util.Date

@Entity(tableName = "chats")
@TypeConverters(DateConverter::class, ListConverter::class)
data class ChatEntity(
    @PrimaryKey
    val id: String,
    val isGroup: Boolean = false,
    val name: String? = null,
    val participants: List<String> = emptyList(),
    val lastMessageContent: String? = null,
    val lastMessageTime: Date? = null,
    val unreadCount: Int = 0,
    val isArchived: Boolean = false,
    val isMuted: Boolean = false,
    val isPinned: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

