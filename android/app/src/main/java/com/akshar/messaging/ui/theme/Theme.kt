package com.akshar.messaging.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Modern Professional Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF128C7E),           // WhatsApp Green
    secondary = Color(0xFF25D366),         // Light Green
    tertiary = Color(0xFF075E54),          // Dark Green
    background = Color(0xFF0B141A),        // Dark Background
    surface = Color(0xFF1F2C34),           // Dark Surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFFE9EDEF),
    onSurface = Color(0xFFE9EDEF)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF128C7E),           // WhatsApp Green
    secondary = Color(0xFF25D366),         // Light Green
    tertiary = Color(0xFF075E54),          // Dark Green
    background = Color(0xFFF7F8FA),        // Light Background
    surface = Color.White,                 // White Surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF111B21),
    onSurface = Color(0xFF111B21)
)

@Composable
fun AksharTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
