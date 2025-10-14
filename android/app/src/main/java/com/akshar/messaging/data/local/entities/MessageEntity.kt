package com.akshar.messaging.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.akshar.messaging.data.local.converters.DateConverter
import java.util.Date

@Entity(tableName = "messages")
@TypeConverters(DateConverter::class)
data class MessageEntity(
    @PrimaryKey
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val type: String = "text",
    val mediaUrl: String? = null,
    val status: String = "sent", // sent, delivered, read
    val isRead: Boolean = false,
    val createdAt: Date = Date(),
    val isSynced: Boolean = true
)

