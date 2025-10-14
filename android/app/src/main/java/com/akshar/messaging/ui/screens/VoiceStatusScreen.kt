package com.akshar.messaging.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceStatusScreen(
    onNavigateBack: () -> Unit,
    onPostVoiceStatus: (String, Long) -> Unit
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }
    var recordingDuration by remember { mutableStateOf(0L) }
    var recordedAudioPath by remember { mutableStateOf<String?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    
    // Animation for recording pulse
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    // Timer for recording duration
    LaunchedEffect(isRecording) {
        while (isRecording) {
            delay(100)
            recordingDuration += 100
        }
    }
    
    // Cleanup MediaPlayer when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Voice status",
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
                    TextButton(
                        onClick = {
                            recordedAudioPath?.let { audioPath ->
                                onPostVoiceStatus(audioPath, recordingDuration)
                            }
                        },
                        enabled = recordedAudioPath != null
                    ) {
                        Text(
                            "POST",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Recording Visual
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(
                        if (isRecording) Color(0xFF2E7D32) else Color(0xFF424242),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Recording Animation
                if (isRecording) {
                    Box(
                        modifier = Modifier
                            .size(250.dp)
                            .scale(pulseScale)
                            .background(
                                Color(0xFF2E7D32).copy(alpha = 0.3f),
                                CircleShape
                            )
                    )
                }
                
                // Center Content
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.Mic,
                        contentDescription = "Microphone",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    if (isRecording) {
                        Text(
                            text = "Recording...",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = formatDuration(recordingDuration),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    } else if (recordedAudioPath != null) {
                        Text(
                            text = "Voice Message",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = formatDuration(recordingDuration),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    } else {
                        Text(
                            text = "Tap to record",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hold to record voice status",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Controls
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (recordedAudioPath != null && !isRecording) {
                    // Play/Pause Button
                    IconButton(
                        onClick = { 
                            if (isPlaying) {
                                // Stop playback
                                mediaPlayer?.stop()
                                mediaPlayer?.release()
                                mediaPlayer = null
                                isPlaying = false
                            } else {
                                // Start playback
                                try {
                                    if (mediaPlayer != null) {
                                        mediaPlayer?.release()
                                    }
                                    
                                    // For demo purposes, we'll simulate audio playback
                                    // In real implementation, use the recorded audio file
                                    mediaPlayer = MediaPlayer().apply {
                                        // Note: In real app, set audio source to recorded file
                                        // setDataSource(recordedAudioPath)
                                        // For demo, we'll just simulate playback
                                        setOnCompletionListener {
                                            isPlaying = false
                                        }
                                        prepare()
                                        start()
                                    }
                                    isPlaying = true
                                } catch (e: IOException) {
                                    android.util.Log.e("VoiceStatus", "Error playing audio: ${e.message}")
                                    isPlaying = false
                                }
                            }
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2E7D32))
                    ) {
                        Icon(
                            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    // Delete Button
                    IconButton(
                        onClick = { 
                            recordedAudioPath = null
                            recordingDuration = 0L
                        },
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE91E63))
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                } else {
                    // Record Button
                    IconButton(
                        onClick = { 
                            if (isRecording) {
                                // Stop recording
                                isRecording = false
                                recordedAudioPath = "voice_${System.currentTimeMillis()}.m4a" // Simulated path
                            } else {
                                // Start recording
                                isRecording = true
                                recordingDuration = 0L
                                recordedAudioPath = null
                            }
                        },
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(
                                if (isRecording) Color(0xFFE91E63) else Color(0xFF2E7D32)
                            )
                    ) {
                        Icon(
                            if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                            contentDescription = if (isRecording) "Stop" else "Record",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Instructions
            Text(
                text = if (recordedAudioPath != null) {
                    "Tap play to preview your voice status"
                } else if (isRecording) {
                    "Tap stop when you're done recording"
                } else {
                    "Record a voice message to share with your contacts"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            
            // Voice Waveform (Simulated)
            if (isRecording || recordedAudioPath != null) {
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(20) { index ->
                        val height = if (isRecording) {
                            (20..60).random().dp
                        } else {
                            (15..40).random().dp
                        }
                        
                        Box(
                            modifier = Modifier
                                .width(4.dp)
                                .height(height)
                                .background(
                                    Color(0xFF2E7D32),
                                    RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}

private fun formatDuration(durationMs: Long): String {
    val seconds = (durationMs / 1000) % 60
    val minutes = (durationMs / (1000 * 60)) % 60
    
    return if (minutes > 0) {
        String.format("%d:%02d", minutes, seconds)
    } else {
        String.format("0:%02d", seconds)
    }
}
