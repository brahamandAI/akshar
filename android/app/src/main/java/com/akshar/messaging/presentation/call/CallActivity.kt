package com.akshar.messaging.presentation.call

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.akshar.messaging.data.webrtc.CallState

class CallActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val callerName = intent.getStringExtra("caller_name") ?: "Unknown"
        val isVideoCall = intent.getBooleanExtra("is_video_call", false)
        
        setContent {
            MaterialTheme {
                CallScreen(
                    callerName = callerName,
                    isVideoCall = isVideoCall,
                    onEndCall = { finish() }
                )
            }
        }
    }
}

@Composable
fun CallScreen(
    callerName: String,
    isVideoCall: Boolean,
    onEndCall: () -> Unit
) {
    var callState by remember { mutableStateOf<CallState>(CallState.Connecting) }
    var isMuted by remember { mutableStateOf(false) }
    var isVideoEnabled by remember { mutableStateOf(isVideoCall) }
    var isSpeakerOn by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section - Caller info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 48.dp)
            ) {
                // Avatar
                Surface(
                    modifier = Modifier.size(100.dp),
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = callerName.first().toString(),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = callerName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = when (callState) {
                        is CallState.Connecting -> "Connecting..."
                        is CallState.Ringing -> "Ringing..."
                        is CallState.Connected -> "Connected"
                        is CallState.Disconnected -> "Disconnected"
                        is CallState.Failed -> "Call Failed"
                        is CallState.Idle -> ""
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Bottom section - Call controls
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 48.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Mute button
                    CallButton(
                        icon = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        label = if (isMuted) "Unmute" else "Mute",
                        onClick = { isMuted = !isMuted },
                        backgroundColor = if (isMuted) 
                            MaterialTheme.colorScheme.error 
                        else 
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                    
                    // Speaker button
                    CallButton(
                        icon = if (isSpeakerOn) Icons.Default.VolumeUp else Icons.Default.VolumeDown,
                        label = "Speaker",
                        onClick = { isSpeakerOn = !isSpeakerOn },
                        backgroundColor = if (isSpeakerOn) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                    
                    // Video toggle (if video call)
                    if (isVideoCall) {
                        CallButton(
                            icon = if (isVideoEnabled) Icons.Default.Videocam else Icons.Default.VideocamOff,
                            label = "Video",
                            onClick = { isVideoEnabled = !isVideoEnabled },
                            backgroundColor = if (isVideoEnabled) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.error
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // End call button
                FloatingActionButton(
                    onClick = onEndCall,
                    containerColor = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(72.dp)
                ) {
                    Icon(
                        Icons.Default.CallEnd,
                        contentDescription = "End Call",
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }
    }
}

@Composable
fun CallButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    backgroundColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = backgroundColor,
            modifier = Modifier.size(56.dp)
        ) {
            Icon(icon, contentDescription = label)
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

