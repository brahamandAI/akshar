package com.akshar.messaging.data.models

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("_id") val _id: String,
    val user: User,
    val mediaUrl: String,
    val mediaType: String, // "image" or "video"
    val caption: String? = null,
    val viewedBy: List<String> = emptyList(),
    val createdAt: String,
    val expiresAt: String
)

data class CreateStatusRequest(
    val mediaUrl: String,
    val mediaType: String,
    val caption: String? = null
)

data class OldStatusResponse(
    val success: Boolean,
    val status: Status
)

data class StatusesResponse(
    val success: Boolean,
    val statuses: List<Status>
)

