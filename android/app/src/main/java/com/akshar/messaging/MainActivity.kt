package com.akshar.messaging

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
// import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.akshar.messaging.ui.navigation.AksharNavigation
import com.akshar.messaging.ui.theme.AksharTheme
import com.akshar.messaging.ui.auth.AuthViewModel
// import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.launch

// @AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel by lazy { AuthViewModel(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before super.onCreate()
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // No hardcoded token - will use proper authentication flow
        
        try {
            enableEdgeToEdge()

            setContent {
                AksharTheme {
                    AksharApp(authViewModel = authViewModel)
                }
            }
        } catch (e: Exception) {
            Log.e("AksharApp", "Error in onCreate: ${e.message}", e)
            // Show error screen or fallback
            setContent {
                AksharTheme {
                    ErrorScreen(error = e.message ?: "Unknown error")
                }
            }
        }
    }

    private fun testAppConnection() {
        try {
            Log.d("AksharApp", "🚀 App initialized successfully")
            Log.d("AksharApp", "📱 Ready for functionality")
        } catch (e: Exception) {
            Log.e("AksharApp", "❌ App initialization failed: ${e.message}")
        }
    }
}

@Composable
fun AksharApp(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    var hasError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            // Simulate app initialization if needed
        } catch (e: Exception) {
            hasError = e.message
        }
    }

    if (hasError != null) {
        ErrorScreen(error = hasError ?: "Unknown Error")
    } else {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AksharNavigation(
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}

@Composable
fun ErrorScreen(error: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Akshar Messaging",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "App Error",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = error,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { /* Restart app */ }
        ) {
            Text("Try Again")
        }
    }
}
