package com.akshar.messaging.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akshar.messaging.data.models.Message
import com.akshar.messaging.utils.MediaPickerUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatName: String,
    messages: List<Message>,
    isTyping: Boolean,
    typingUsers: List<String>,
    isConnected: Boolean,
    onSendMessage: (String) -> Unit,
    onSendImage: (android.net.Uri) -> Unit,
    onSendVideo: (android.net.Uri) -> Unit,
    onStartTyping: () -> Unit,
    onStopTyping: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var messageText by remember { mutableStateOf("") }
    var showMediaOptions by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    
    // Image picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                onSendImage(uri)
            }
        }
    }
    
    // Video picker
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                onSendVideo(uri)
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(chatName, fontWeight = FontWeight.Bold)
                        if (isConnected) {
                            if (typingUsers.isNotEmpty()) {
                                Text(
                                    "${typingUsers.joinToString(", ")} typing...",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            } else {
                                Text(
                                    "Online",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        } else {
                            Text(
                                "Connecting...",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Voice call */ }) {
                        Icon(Icons.Default.Call, contentDescription = "Voice call")
                    }
                    IconButton(onClick = { /* TODO: Video call */ }) {
                        Icon(Icons.Default.Videocam, contentDescription = "Video call")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Attachment button
                    IconButton(onClick = { showMediaOptions = !showMediaOptions }) {
                        Icon(Icons.Default.Add, contentDescription = "Attach")
                    }
                    
                    // Message input
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { 
                            messageText = it
                            if (it.isNotEmpty()) {
                                onStartTyping()
                            } else {
                                onStopTyping()
                            }
                        },
                        placeholder = { Text("Type a message...") },
                        modifier = Modifier.weight(1f),
                        maxLines = 5
                    )
                    
                    // Send button
                    IconButton(
                        onClick = {
                            if (messageText.isNotBlank()) {
                                onSendMessage(messageText)
                                messageText = ""
                                onStopTyping()
                            }
                        },
                        enabled = messageText.isNotBlank()
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "Send")
                    }
                }
            }
            
            // Media options
            if (showMediaOptions) {
                Surface(
                    shadowElevation = 4.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(
                                onClick = {
                                    MediaPickerUtils.pickImage(imagePickerLauncher)
                                    showMediaOptions = false
                                }
                            ) {
                                Icon(Icons.Default.Image, contentDescription = "Image")
                            }
                            Text("Photo", style = MaterialTheme.typography.bodySmall)
                        }
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(
                                onClick = {
                                    MediaPickerUtils.pickVideo(videoPickerLauncher)
                                    showMediaOptions = false
                                }
                            ) {
                                Icon(Icons.Default.Videocam, contentDescription = "Video")
                            }
                            Text("Video", style = MaterialTheme.typography.bodySmall)
                        }
                        
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(
                                onClick = {
                                    // TODO: File picker
                                    showMediaOptions = false
                                }
                            ) {
                                Icon(Icons.Default.AttachFile, contentDescription = "File")
                            }
                            Text("File", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        // Messages list
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 8.dp),
            state = listState,
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message = message)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    val isCurrentUser = false // TODO: Get from auth
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (isCurrentUser) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                if (!isCurrentUser) {
                    Text(
                        text = message.sender.firstName,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                
                // Message content
                when (message.contentType) {
                    "image" -> {
                        Text("📷 Image", style = MaterialTheme.typography.bodyMedium)
                    }
                    "video" -> {
                        Text("🎥 Video", style = MaterialTheme.typography.bodyMedium)
                    }
                    else -> {
                        Text(
                            text = message.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isCurrentUser) 
                                MaterialTheme.colorScheme.onPrimary 
                            else 
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = formatChatTime(message.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isCurrentUser) 
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun formatChatTime(timestamp: String): String {
    // TODO: Proper time formatting
    return timestamp.substringAfter("T").substringBefore(".")
}

