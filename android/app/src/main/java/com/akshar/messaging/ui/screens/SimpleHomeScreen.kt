package com.akshar.messaging.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleHomeScreen(
    onLogoutClick: () -> Unit
) {
    // Sample chat data
    val chats = listOf(
        ChatItem("John Doe", "Hey, how are you?", "2:30 PM", true),
        ChatItem("Sarah Smith", "See you tomorrow!", "1:45 PM", false),
        ChatItem("Mike Johnson", "Thanks for the help", "12:20 PM", false),
        ChatItem("Emma Wilson", "Can we meet today?", "11:15 AM", true),
        ChatItem("Alex Brown", "The meeting was great", "Yesterday", false)
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Akshar Messaging",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge
                )
                
                TextButton(onClick = onLogoutClick) {
                    Text(
                        "Logout",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        
        // Search Bar
        OutlinedTextField(
            value = "",
            onValueChange = { },
            placeholder = { Text("Search chats...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        
        // Chats List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(chats) { chat ->
                SimpleChatListItem(chat = chat)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SimpleChatListItem(chat: ChatItem) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Surface(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = chat.name.first().toString(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Chat Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Text(
                        text = chat.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.lastMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    
                    if (chat.hasUnread) {
                        Surface(
                            modifier = Modifier.size(8.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {}
                    }
                }
            }
        }
    }
}

data class ChatItem(
    val name: String,
    val lastMessage: String,
    val time: String,
    val hasUnread: Boolean
)
