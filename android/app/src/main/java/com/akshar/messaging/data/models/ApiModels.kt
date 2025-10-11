package com.akshar.messaging.data.models

import com.google.gson.annotations.SerializedName

// Request Models
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)

data class LoginRequest(
    val emailOrUsername: String,
    val password: String
)

data class UpdateProfileRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val bio: String? = null,
    val avatar: String? = null
)

data class CreateChatRequest(
    val participantId: String,  // For direct chats (/api/chats)
    val isGroup: Boolean = false,
    val name: String? = null
)

data class CreateGroupRequest(
    val groupName: String,      // For group chats (/api/chats/group)
    val participants: List<String>,
    val description: String? = null
)

data class SendMessageRequest(
    val content: String,
    val type: String = "text",
    val replyTo: String? = null,
    val mentions: List<String>? = null
)

// Response Models
data class ApiResponse(
    val success: Boolean,
    val message: String,
    val status: StatusResponse? = null,
    val statuses: List<StatusResponse>? = null,
    val viewCount: Int? = null
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val data: AuthData? = null
)

data class AuthData(
    val user: User,
    val token: String,
    val refreshToken: String? = null
)

data class UserResponse(
    val success: Boolean,
    val user: User? = null,  // Direct access (legacy)
    val data: UserData? = null  // Nested structure (current backend)
)

data class UserData(
    val user: User
)

data class UsersResponse(
    val success: Boolean,
    val users: List<User>? = null,  // Direct access (legacy)
    val data: UsersData? = null     // Nested structure (current backend)
)

data class UsersData(
    val users: List<User>,
    val total: Int,
    val limit: Int,
    val skip: Int
)

data class ChatResponse(
    val success: Boolean,
    val message: String? = null,
    val chat: Chat? = null,  // Direct access (legacy)
    val data: ChatData? = null  // Nested structure (current backend)
)

data class ChatData(
    val chat: Chat
)

data class ChatsResponse(
    val success: Boolean,
    val chats: List<Chat>
)

data class MessageResponse(
    val success: Boolean,
    val message: Message
)

data class MessagesResponse(
    val success: Boolean,
    val messages: List<Message>
)

data class UploadResponse(
    val success: Boolean,
    val data: UploadData
)

data class UploadData(
    val url: String,
    val publicId: String
)

// Domain Models
data class User(
    @SerializedName("_id") val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val avatar: String? = null,
    val status: String = "offline",
    val lastSeen: String? = null,
    val bio: String? = null,
    val phoneNumber: String? = null
)

data class Chat(
    @SerializedName("_id") val _id: String,
    val name: String? = null,
    val isGroup: Boolean = false,
    val participants: List<User>,
    val lastMessage: Message? = null,
    val admin: String? = null,
    val isArchived: Boolean = false,
    val createdAt: String,
    val updatedAt: String
)

data class Message(
    @SerializedName("_id") val id: String,
    val sender: User,
    val chat: String,
    val content: String,
    val contentType: String = "text",
    val mediaUrl: String? = null,
    val readBy: List<ReadReceipt> = emptyList(),
    val reactions: List<Reaction> = emptyList(),
    val createdAt: String
)

data class ReadReceipt(
    val user: String,
    val readAt: String
)

data class Reaction(
    val user: String,
    val emoji: String
)

// Status Models
data class StatusRequest(
    val type: String,
    val content: String,
    val backgroundColor: String? = null,
    val fontFamily: String? = null,
    val songTitle: String? = null,
    val artist: String? = null,
    val duration: String? = null,
    val template: LayoutTemplate? = null,
    val audioPath: String? = null,
    val audioDuration: Long? = null,
    val imagePath: String? = null
)

data class StatusResponse(
    val id: String,
    val type: String,
    val content: String,
    val backgroundColor: String?,
    val fontFamily: String?,
    val songTitle: String?,
    val artist: String?,
    val duration: String?,
    val template: LayoutTemplate?,
    val audioPath: String?,
    val audioDuration: Long?,
    val imagePath: String?,
    val viewCount: Int,
    val hasUserViewed: Boolean,
    val createdAt: String,
    val expiresAt: String,
    val user: StatusUser
)

data class StatusUser(
    val id: String,
    val name: String,
    val email: String,
    val profilePicture: String?
)

data class LayoutTemplate(
    val name: String?,
    val backgroundColor: String?,
    val tags: List<String>?,
    val defaultText: String?
)

data class StatusListResponse(
    val success: Boolean,
    val message: String,
    val statuses: List<StatusResponse>,
    val pagination: PaginationInfo
)

data class PaginationInfo(
    val page: Int,
    val limit: Int,
    val total: Int
)

data class StatusStatsResponse(
    val success: Boolean,
    val message: String,
    val stats: StatusStats
)

data class StatusStats(
    val totalStatuses: Int,
    val totalViews: Int
)

data class FileUploadResponse(
    val success: Boolean,
    val message: String,
    val audioPath: String? = null,
    val imagePath: String? = null,
    val publicId: String? = null
)

