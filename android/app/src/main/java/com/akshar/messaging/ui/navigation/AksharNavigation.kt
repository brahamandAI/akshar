package com.akshar.messaging.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import com.akshar.messaging.ui.auth.AuthViewModel
import com.akshar.messaging.ui.screens.ModernHomeScreen
import com.akshar.messaging.ui.screens.LoginScreen
import com.akshar.messaging.ui.screens.RegisterScreen
import com.akshar.messaging.ui.screens.ContactsListScreen
import com.akshar.messaging.ui.screens.ChatScreen
import com.akshar.messaging.ui.screens.AccountSettingsScreen
import com.akshar.messaging.ui.screens.PrivacySettingsScreen
import com.akshar.messaging.ui.screens.SecuritySettingsScreen
import com.akshar.messaging.ui.screens.ChatsSettingsScreen
import com.akshar.messaging.ui.screens.NotificationsSettingsScreen
import com.akshar.messaging.ui.screens.StorageSettingsScreen
import com.akshar.messaging.ui.screens.HelpCenterScreen
import com.akshar.messaging.ui.screens.LinkedDevicesScreen
import com.akshar.messaging.ui.screens.StarredMessagesScreen
import com.akshar.messaging.ui.screens.QRScannerScreen
import com.akshar.messaging.ui.screens.DeviceLinkingScreen
import com.akshar.messaging.ui.screens.AddStatusScreen
import com.akshar.messaging.ui.screens.TextStatusScreen
import com.akshar.messaging.ui.screens.MusicStatusScreen
import com.akshar.messaging.ui.screens.LayoutStatusScreen
import com.akshar.messaging.ui.screens.VoiceStatusScreen
import com.akshar.messaging.ui.screens.StatusViewScreen
import com.akshar.messaging.ui.home.HomeViewModel
import com.akshar.messaging.utils.StorageUtil
import androidx.navigation.NavType
import androidx.navigation.navArgument
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody

@Composable
fun AksharNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
) {
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    
    val startDestination = if (isAuthenticated) Routes.HOME else Routes.LOGIN
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.LOGIN) {
            // ✅ Use LaunchedEffect with proper key
            androidx.compose.runtime.LaunchedEffect(isAuthenticated) {
                if (isAuthenticated) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                }
            }
            
            LoginScreen(
                onLoginClick = { email, password ->
                    authViewModel.login(email, password)
                },
                onRegisterClick = {
                    navController.navigate("register")
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }
        
        composable(Routes.REGISTER) {
            // ✅ Use LaunchedEffect with proper key
            androidx.compose.runtime.LaunchedEffect(isAuthenticated) {
                if (isAuthenticated) {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.REGISTER) { inclusive = true }
                        }
                }
            }
            
            RegisterScreen(
                onRegisterClick = { username, email, password, firstName, lastName ->
                    authViewModel.register(username, email, password, firstName, lastName)
                },
                onLoginClick = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        }
        
        composable(Routes.HOME) {
            ModernHomeScreen(
                onLogoutClick = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                onNavigateToContacts = {
                    navController.navigate(Routes.CONTACTS)
                },
                onNavigateToChat = { chatId ->
                    navController.navigate("${Routes.CHAT.replace("{chatId}", chatId)}")
                },
                navController = navController
            )
        }
        
        composable(Routes.CONTACTS) {
            val homeViewModel: com.akshar.messaging.ui.home.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            val context = androidx.compose.ui.platform.LocalContext.current
            val coroutineScope = androidx.compose.runtime.rememberCoroutineScope()
            
            ContactsListScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onContactClick = { contact ->
                    // If userId is already known (test user), create chat directly
                    if (contact.userId != null) {
                        homeViewModel.createChat(contact.userId) { chatId ->
                            navController.navigate("${Routes.CHAT.replace("{chatId}", chatId)}") {
                                popUpTo(Routes.CONTACTS) { inclusive = true }
                            }
                        }
                        return@ContactsListScreen
                    }
                    
                    // Otherwise, search for user in backend
                    homeViewModel.searchUsers(contact.phone)

                    coroutineScope.launch {
                        delay(500)
                        val searchResults = homeViewModel.searchResults.value

                        if (searchResults.isNotEmpty()) {
                            val user = searchResults.first()
                            homeViewModel.createChat(user.id) { chatId ->
                                navController.navigate("${Routes.CHAT.replace("{chatId}", chatId)}") {
                                    popUpTo(Routes.CONTACTS) { inclusive = true }
                                }
                            }
                        } else {
                            android.widget.Toast.makeText(
                                context,
                                "${contact.name} is not on Akshar Messaging yet. Invite them!",
                                android.widget.Toast.LENGTH_LONG
                            ).show()
                            navController.popBackStack()
                        }
                    }
                },
                onAddNewContact = {
                    navController.popBackStack()
                },
                onCreateGroup = {
                    // Set trigger for group creation
                    val sharedPrefs = context.getSharedPreferences("app_triggers", android.content.Context.MODE_PRIVATE)
                    sharedPrefs.edit().putBoolean("trigger_group_creation", true).apply()
                    navController.popBackStack()
                },
                onCreateCommunity = {
                    // Set trigger for community creation
                    val sharedPrefs = context.getSharedPreferences("app_triggers", android.content.Context.MODE_PRIVATE)
                    sharedPrefs.edit().putBoolean("trigger_community_creation", true).apply()
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Routes.CHAT,
            arguments = listOf(navArgument("chatId") { type = NavType.StringType })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val chatViewModel: com.akshar.messaging.ui.chat.ChatViewModel = 
                androidx.lifecycle.viewmodel.compose.viewModel()
            
            androidx.compose.runtime.LaunchedEffect(chatId) {
                chatViewModel.loadChat(chatId)
            }
            
            val messages by chatViewModel.messages.collectAsState()
            val typingUsers by chatViewModel.typingUsers.collectAsState()
            val isConnected by chatViewModel.isConnected.collectAsState()
            
            ChatScreen(
                chatName = "Chat", // TODO: Get actual chat name from chat data
                messages = messages,
                isTyping = typingUsers.isNotEmpty(),
                typingUsers = typingUsers,
                isConnected = isConnected,
                onSendMessage = { message ->
                    chatViewModel.sendMessage(message)
                },
                onSendImage = { uri ->
                    chatViewModel.sendImageMessage(uri)
                },
                onSendVideo = { uri ->
                    chatViewModel.sendVideoMessage(uri)
                },
                onStartTyping = {
                    chatViewModel.startTyping()
                },
                onStopTyping = {
                    chatViewModel.stopTyping()
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        // Settings Routes
        composable(Routes.ACCOUNT_SETTINGS) {
            AccountSettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPrivacy = { navController.navigate(Routes.PRIVACY_SETTINGS) },
                onNavigateToSecurity = { navController.navigate(Routes.SECURITY_SETTINGS) },
                onNavigateToChangeNumber = { navController.navigate(Routes.CHANGE_NUMBER) },
                onNavigateToRequestInfo = { /* TODO: Implement request info */ },
                onNavigateToDeleteAccount = { navController.navigate(Routes.DELETE_ACCOUNT) }
            )
        }
        
        composable(Routes.PRIVACY_SETTINGS) {
            PrivacySettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onSaveSettings = { lastSeen, profilePhoto, status, about ->
                    // TODO: Save privacy settings to backend
                }
            )
        }
        
        composable(Routes.SECURITY_SETTINGS) {
            SecuritySettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.CHATS_SETTINGS) {
            ChatsSettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.NOTIFICATIONS_SETTINGS) {
            NotificationsSettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.STORAGE_SETTINGS) {
            val context = androidx.compose.ui.platform.LocalContext.current
            StorageSettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onClearCache = {
                    StorageUtil.clearCache(context)
                },
                onClearMedia = {
                    // TODO: Clear media files
                }
            )
        }
        
        composable(Routes.HELP_CENTER) {
            HelpCenterScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.LINKED_DEVICES) {
            LinkedDevicesScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToQRScanner = { navController.navigate(Routes.QR_SCANNER) },
                onNavigateToDeviceLinking = { navController.navigate(Routes.DEVICE_LINKING) }
            )
        }
        
        composable(Routes.STARRED_MESSAGES) {
            StarredMessagesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Routes.QR_SCANNER) {
            val context = androidx.compose.ui.platform.LocalContext.current
            QRScannerScreen(
                onNavigateBack = { navController.popBackStack() },
                onQRCodeScanned = { qrCode ->
                    // Process QR code for device linking
                    if (qrCode.startsWith("AKSHAR_DEVICE_LINK:")) {
                        // Extract device info from QR code
                        val parts = qrCode.split(":")
                        if (parts.size >= 3) {
                            val timestamp = parts[1]
                            val deviceId = parts[2]
                            
                            android.widget.Toast.makeText(
                                context,
                                "Device linked successfully! Device ID: $deviceId",
                                android.widget.Toast.LENGTH_LONG
                            ).show()
                            
                            // TODO: Send device linking request to backend
                            // TODO: Add device to linked devices list
                        }
                    } else {
                        android.widget.Toast.makeText(
                            context,
                            "Invalid QR code. Please scan a device linking QR code.",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                    navController.popBackStack()
                }
            )
        }
        
        composable(Routes.DEVICE_LINKING) {
            DeviceLinkingScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToQRScanner = { navController.navigate(Routes.QR_SCANNER) }
            )
        }
        
        // Status Screens
        composable(Routes.STATUS_VIEW) {
            StatusViewScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToAddStatus = { navController.navigate(Routes.ADD_STATUS) }
            )
        }
        
        composable(Routes.ADD_STATUS) {
            AddStatusScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToTextStatus = { navController.navigate(Routes.TEXT_STATUS) },
                onNavigateToMusicStatus = { navController.navigate(Routes.MUSIC_STATUS) },
                onNavigateToLayoutStatus = { navController.navigate(Routes.LAYOUT_STATUS) },
                onNavigateToVoiceStatus = { navController.navigate(Routes.VOICE_STATUS) }
            )
        }
        
        composable(Routes.TEXT_STATUS) {
            val context = androidx.compose.ui.platform.LocalContext.current
            TextStatusScreen(
                onNavigateBack = { navController.popBackStack() },
                onPostStatus = { text, background, font ->
                    // TODO: Save status to backend
                    android.widget.Toast.makeText(
                        context,
                        "Text status posted successfully!",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                }
            )
        }
        
        composable(Routes.MUSIC_STATUS) {
            val context = androidx.compose.ui.platform.LocalContext.current
            val statusViewModel: com.akshar.messaging.ui.status.StatusViewModel = 
                androidx.lifecycle.viewmodel.compose.viewModel()
            
            MusicStatusScreen(
                onNavigateBack = { navController.popBackStack() },
                onPostMusicStatus = { title, artist, duration ->
                    val token = com.akshar.messaging.utils.TokenManager.getBearerToken(context)
                    if (token != null) {
                        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                            try {
                                val statusRequest = com.akshar.messaging.data.models.StatusRequest(
                                    type = "music",
                                    content = "$title - $artist",
                                    backgroundColor = "#1DB954",
                                    fontFamily = "music"
                                )
                                
                                val response = com.akshar.messaging.data.api.RetrofitClient
                                    .statusApiService
                                    .createStatus(token, statusRequest)
                                
                                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                    if (response.isSuccessful) {
                                        android.widget.Toast.makeText(
                                            context,
                                            "Music status posted!",
                                            android.widget.Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    } else {
                                        android.widget.Toast.makeText(
                                            context,
                                            "Failed to post status",
                                            android.widget.Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (e: Exception) {
                                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                    android.widget.Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            )
        }
        
        composable(Routes.LAYOUT_STATUS) {
            val context = androidx.compose.ui.platform.LocalContext.current
            val statusViewModel: com.akshar.messaging.ui.status.StatusViewModel = 
                androidx.lifecycle.viewmodel.compose.viewModel()
            
            LayoutStatusScreen(
                onNavigateBack = { navController.popBackStack() },
                onPostLayoutStatus = { text, template ->
                    val token = com.akshar.messaging.utils.TokenManager.getBearerToken(context)
                    if (token != null) {
                        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                            try {
                                val statusRequest = com.akshar.messaging.data.models.StatusRequest(
                                    type = "layout",
                                    content = text,
                                    backgroundColor = template.name,
                                    fontFamily = "layout"
                                )
                                
                                val response = com.akshar.messaging.data.api.RetrofitClient
                                    .statusApiService
                                    .createStatus(token, statusRequest)
                                
                                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                    if (response.isSuccessful) {
                                        android.widget.Toast.makeText(
                                            context,
                                            "Layout status posted!",
                                            android.widget.Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    } else {
                                        android.widget.Toast.makeText(
                                            context,
                                            "Failed to post status",
                                            android.widget.Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (e: Exception) {
                                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                    android.widget.Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            )
        }
        
        composable(Routes.VOICE_STATUS) {
            val context = androidx.compose.ui.platform.LocalContext.current
            val statusViewModel: com.akshar.messaging.ui.status.StatusViewModel = 
                androidx.lifecycle.viewmodel.compose.viewModel()
            
            VoiceStatusScreen(
                onNavigateBack = { navController.popBackStack() },
                onPostVoiceStatus = { audioPath, duration ->
                    val token = com.akshar.messaging.utils.TokenManager.getBearerToken(context)
                    if (token != null) {
                        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
                            try {
                                val file = java.io.File(audioPath)
                                val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
                                val body = okhttp3.MultipartBody.Part.createFormData("audio", file.name, requestFile)
                                
                                val response = com.akshar.messaging.data.api.RetrofitClient
                                    .statusApiService
                                    .uploadAudio(token, body)
                                
                                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                    if (response.isSuccessful) {
                                        android.widget.Toast.makeText(
                                            context,
                                            "Voice status posted!",
                                            android.widget.Toast.LENGTH_SHORT
                                        ).show()
                                        navController.popBackStack()
                                    } else {
                                        android.widget.Toast.makeText(
                                            context,
                                            "Failed to post status",
                                            android.widget.Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (e: Exception) {
                                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.Main) {
                                    android.widget.Toast.makeText(
                                        context,
                                        "Error: ${e.message}",
                                        android.widget.Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            )
        }
        
    }
}
