package com.akshar.messaging.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusViewScreen(
    onNavigateBack: () -> Unit,
    onNavigateToAddStatus: () -> Unit
) {
    val statusUpdates = remember {
        listOf(
            StatusUpdate(
                id = "1",
                userName = "John Doe",
                userAvatar = null,
                timestamp = System.currentTimeMillis() - 300000, // 5 minutes ago
                type = StatusType.TEXT,
                content = "Just finished my morning run! Feeling energized and ready for the day. 💪",
                backgroundColor = Color(0xFF2E7D32),
                fontFamily = null
            ),
            StatusUpdate(
                id = "2",
                userName = "Sarah Wilson",
                userAvatar = null,
                timestamp = System.currentTimeMillis() - 900000, // 15 minutes ago
                type = StatusType.MUSIC,
                content = "Shape of You - Ed Sheeran",
                backgroundColor = Color(0xFF1976D2),
                fontFamily = null
            ),
            StatusUpdate(
                id = "3",
                userName = "Mike Johnson",
                userAvatar = null,
                timestamp = System.currentTimeMillis() - 1800000, // 30 minutes ago
                type = StatusType.LAYOUT,
                content = "PRO TIP: Always backup your data before updating software!",
                backgroundColor = Color(0xFFFF5722),
                fontFamily = null
            ),
            StatusUpdate(
                id = "4",
                userName = "Emma Davis",
                userAvatar = null,
                timestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                type = StatusType.VOICE,
                content = "Voice message (0:45)",
                backgroundColor = Color(0xFF7B1FA2),
                fontFamily = null
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Status",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Normal
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToAddStatus) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Add Status")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddStatus,
                containerColor = Color(0xFF2E7D32)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Add Status")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                // My Status
                MyStatusCard(
                    onClick = onNavigateToAddStatus,
                    hasStatus = false
                )
                
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
            
            item {
                Text(
                    text = "Recent updates",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            items(statusUpdates) { status ->
                StatusCard(
                    status = status,
                    onClick = { /* TODO: Navigate to full status view */ }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun MyStatusCard(
    onClick: () -> Unit,
    hasStatus: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E7D32)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "My Profile",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "My Status",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (hasStatus) "Tap to view your status" else "Tap to add status update",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = Color(0xFF2E7D32)
            )
        }
    }
}

@Composable
fun StatusCard(
    status: StatusUpdate,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Status Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(status.backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                when (status.type) {
                    StatusType.TEXT -> {
                        Text(
                            text = status.content,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            maxLines = 3,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    StatusType.MUSIC -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.MusicNote,
                                contentDescription = "Music",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = status.content,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    StatusType.LAYOUT -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Lightbulb,
                                contentDescription = "Tip",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "PRO TIP",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = status.content,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    StatusType.VOICE -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Mic,
                                contentDescription = "Voice",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = status.content,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
            
            // User Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "User",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = status.userName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = formatTimestamp(status.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                
                Icon(
                    when (status.type) {
                        StatusType.TEXT -> Icons.Default.Edit
                        StatusType.MUSIC -> Icons.Default.MusicNote
                        StatusType.LAYOUT -> Icons.Default.GridView
                        StatusType.VOICE -> Icons.Default.Mic
                    },
                    contentDescription = status.type.name,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        else -> "${diff / 86400000}d ago"
    }
}

data class StatusUpdate(
    val id: String,
    val userName: String,
    val userAvatar: String?,
    val timestamp: Long,
    val type: StatusType,
    val content: String,
    val backgroundColor: Color,
    val fontFamily: androidx.compose.ui.text.font.FontFamily?
)

enum class StatusType {
    TEXT, MUSIC, LAYOUT, VOICE
}
