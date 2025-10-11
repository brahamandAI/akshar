package com.akshar.messaging.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextStatusScreen(
    onNavigateBack: () -> Unit,
    onPostStatus: (String, Color, FontFamily) -> Unit,
    statusViewModel: com.akshar.messaging.ui.status.StatusViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var text by remember { mutableStateOf("") }
    var selectedBackground by remember { mutableStateOf(Color(0xFF2E7D32)) }
    var selectedFont by remember { mutableStateOf(FontFamily.Default) }
    
    val backgroundColors = listOf(
        Color(0xFF2E7D32), Color(0xFF1976D2), Color(0xFF7B1FA2), 
        Color(0xFFE91E63), Color(0xFFFF5722), Color(0xFFFF9800),
        Color(0xFF4CAF50), Color(0xFF009688), Color(0xFF795548),
        Color(0xFF607D8B), Color(0xFF9C27B0), Color(0xFF3F51B5)
    )
    
    val fontFamilies = listOf(
        FontFamily.Default,
        FontFamily.Monospace,
        FontFamily.Serif,
        FontFamily.Cursive
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Text status",
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
                            if (text.isNotBlank()) {
                                val token = com.akshar.messaging.utils.TokenManager.getBearerToken(context)
                                if (token != null) {
                                    statusViewModel.createTextStatus(
                                        token = token,
                                        content = text,
                                        backgroundColor = selectedBackground.toString(),
                                        fontFamily = selectedFont.toString()
                                    )
                                    onPostStatus(text, selectedBackground, selectedFont)
                                }
                            }
                        },
                        enabled = text.isNotBlank()
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
                .padding(paddingValues)
        ) {
            // Preview Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(selectedBackground)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = selectedFont,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    ),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                "Type a message...",
                                style = TextStyle(
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 24.sp,
                                    fontFamily = selectedFont,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                        innerTextField()
                    }
                )
            }
            
            // Background Colors
            Text(
                text = "Background",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(backgroundColors) { color ->
                    ColorOption(
                        color = color,
                        isSelected = color == selectedBackground,
                        onClick = { selectedBackground = color }
                    )
                }
            }
            
            // Font Options
            Text(
                text = "Font Style",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(fontFamilies) { fontFamily ->
                    FontOption(
                        fontFamily = fontFamily,
                        isSelected = fontFamily == selectedFont,
                        onClick = { selectedFont = fontFamily }
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ColorOption(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun FontOption(
    fontFamily: FontFamily,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(80.dp)
            .height(48.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF2E7D32) else Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Aa",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = fontFamily,
                    color = if (isSelected) Color.White else Color.Black
                )
            )
        }
    }
}
