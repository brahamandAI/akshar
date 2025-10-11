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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Full-screen Account Settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToChangeNumber: () -> Unit,
    onNavigateToRequestInfo: () -> Unit,
    onNavigateToDeleteAccount: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account Settings", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "Privacy & Security") {
                    SettingsOption(
                        icon = Icons.Default.PrivacyTip,
                        title = "Privacy",
                        subtitle = "Control your privacy settings",
                        onClick = onNavigateToPrivacy
                    )
                    SettingsOption(
                        icon = Icons.Default.Security,
                        title = "Security",
                        subtitle = "Enable two-factor authentication",
                        onClick = onNavigateToSecurity
                    )
                }
            }
            
            item {
                SettingsSection(title = "Account Management") {
                    SettingsOption(
                        icon = Icons.Default.Phone,
                        title = "Change Number",
                        subtitle = "Update your phone number",
                        onClick = onNavigateToChangeNumber
                    )
                    SettingsOption(
                        icon = Icons.Default.Info,
                        title = "Request Account Info",
                        subtitle = "Get a report of your account info",
                        onClick = onNavigateToRequestInfo
                    )
                    SettingsOption(
                        icon = Icons.Default.DeleteForever,
                        title = "Delete My Account",
                        subtitle = "Permanently delete your account",
                        onClick = onNavigateToDeleteAccount,
                        textColor = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

/**
 * Full-screen Privacy Settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySettingsScreen(
    onNavigateBack: () -> Unit,
    onSaveSettings: (Boolean, Boolean, Boolean, Boolean) -> Unit
) {
    var showLastSeen by remember { mutableStateOf(true) }
    var showProfilePhoto by remember { mutableStateOf(true) }
    var showStatus by remember { mutableStateOf(true) }
    var showAbout by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "Who can see my personal info") {
                    SwitchOption(
                        title = "Last Seen",
                        subtitle = "When you were last seen",
                        checked = showLastSeen,
                        onCheckedChange = { showLastSeen = it }
                    )
                    SwitchOption(
                        title = "Profile Photo",
                        subtitle = "Who can see your profile photo",
                        checked = showProfilePhoto,
                        onCheckedChange = { showProfilePhoto = it }
                    )
                    SwitchOption(
                        title = "Status",
                        subtitle = "Who can see your status updates",
                        checked = showStatus,
                        onCheckedChange = { showStatus = it }
                    )
                    SwitchOption(
                        title = "About",
                        subtitle = "Who can see your about info",
                        checked = showAbout,
                        onCheckedChange = { showAbout = it }
                    )
                }
            }
        }
    }
}

/**
 * Full-screen Security Settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecuritySettingsScreen(
    onNavigateBack: () -> Unit
) {
    var twoFactorEnabled by remember { mutableStateOf(false) }
    var loginAlerts by remember { mutableStateOf(true) }
    var showSecurityNotifications by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Security", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "Two-Step Verification") {
                    SwitchOption(
                        title = "Two-Factor Authentication",
                        subtitle = "Add an extra layer of security",
                        checked = twoFactorEnabled,
                        onCheckedChange = { twoFactorEnabled = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Security Alerts") {
                    SwitchOption(
                        title = "Login Alerts",
                        subtitle = "Get notified of new logins",
                        checked = loginAlerts,
                        onCheckedChange = { loginAlerts = it }
                    )
                    SwitchOption(
                        title = "Security Notifications",
                        subtitle = "Get security-related notifications",
                        checked = showSecurityNotifications,
                        onCheckedChange = { showSecurityNotifications = it }
                    )
                }
            }
        }
    }
}

/**
 * Full-screen Chats Settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsSettingsScreen(
    onNavigateBack: () -> Unit
) {
    var enterIsSend by remember { mutableStateOf(false) }
    var mediaVisibility by remember { mutableStateOf(true) }
    var conversationTones by remember { mutableStateOf(true) }
    var chatBackup by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chats", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "Chat Settings") {
                    SwitchOption(
                        title = "Enter is Send",
                        subtitle = "Press Enter to send messages",
                        checked = enterIsSend,
                        onCheckedChange = { enterIsSend = it }
                    )
                    SwitchOption(
                        title = "Media Visibility",
                        subtitle = "Show media in gallery",
                        checked = mediaVisibility,
                        onCheckedChange = { mediaVisibility = it }
                    )
                    SwitchOption(
                        title = "Conversation Tones",
                        subtitle = "Play sounds for messages",
                        checked = conversationTones,
                        onCheckedChange = { conversationTones = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Chat History") {
                    SettingsOption(
                        icon = Icons.Default.Backup,
                        title = "Chat Backup",
                        subtitle = "Backup your chats to cloud",
                        onClick = { chatBackup = !chatBackup }
                    )
                    SettingsOption(
                        icon = Icons.Default.History,
                        title = "Chat History",
                        subtitle = "Manage your chat history",
                        onClick = { /* TODO */ }
                    )
                }
            }
        }
    }
}

/**
 * Full-screen Notifications Settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsSettingsScreen(
    onNavigateBack: () -> Unit
) {
    var messageNotifications by remember { mutableStateOf(true) }
    var groupNotifications by remember { mutableStateOf(true) }
    var callNotifications by remember { mutableStateOf(true) }
    var vibrate by remember { mutableStateOf(true) }
    var popupNotification by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notifications", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "Message Notifications") {
                    SwitchOption(
                        title = "Message Notifications",
                        subtitle = "Get notified of new messages",
                        checked = messageNotifications,
                        onCheckedChange = { messageNotifications = it }
                    )
                    SwitchOption(
                        title = "Group Notifications",
                        subtitle = "Get notified of group messages",
                        checked = groupNotifications,
                        onCheckedChange = { groupNotifications = it }
                    )
                    SwitchOption(
                        title = "Call Notifications",
                        subtitle = "Get notified of incoming calls",
                        checked = callNotifications,
                        onCheckedChange = { callNotifications = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Notification Settings") {
                    SwitchOption(
                        title = "Vibrate",
                        subtitle = "Vibrate on new messages",
                        checked = vibrate,
                        onCheckedChange = { vibrate = it }
                    )
                    SwitchOption(
                        title = "Popup Notification",
                        subtitle = "Show popup on screen",
                        checked = popupNotification,
                        onCheckedChange = { popupNotification = it }
                    )
                }
            }
        }
    }
}

/**
 * Full-screen Storage Settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageSettingsScreen(
    onNavigateBack: () -> Unit,
    onClearCache: () -> Unit,
    onClearMedia: () -> Unit
) {
    var autoDownloadMedia by remember { mutableStateOf(true) }
    var downloadOverMobileData by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Storage and Data", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "Network Usage") {
                    SwitchOption(
                        title = "Auto-Download Media",
                        subtitle = "Automatically download media",
                        checked = autoDownloadMedia,
                        onCheckedChange = { autoDownloadMedia = it }
                    )
                    SwitchOption(
                        title = "Download Over Mobile Data",
                        subtitle = "Download media using mobile data",
                        checked = downloadOverMobileData,
                        onCheckedChange = { downloadOverMobileData = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Storage Management") {
                    SettingsOption(
                        icon = Icons.Default.Delete,
                        title = "Clear Cache",
                        subtitle = "Free up storage space",
                        onClick = onClearCache
                    )
                    SettingsOption(
                        icon = Icons.Default.DeleteSweep,
                        title = "Clear Media",
                        subtitle = "Delete downloaded media files",
                        onClick = onClearMedia
                    )
                }
            }
        }
    }
}

/**
 * Full-screen Help Center
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpCenterScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SettingsSection(title = "Support") {
                    SettingsOption(
                        icon = Icons.Default.Help,
                        title = "Help Center",
                        subtitle = "Find answers to common questions",
                        onClick = { /* TODO: Open help center */ }
                    )
                    SettingsOption(
                        icon = Icons.Default.ContactSupport,
                        title = "Contact Us",
                        subtitle = "Get in touch with our support team",
                        onClick = { /* TODO: Open contact form */ }
                    )
                    SettingsOption(
                        icon = Icons.Default.BugReport,
                        title = "Report a Problem",
                        subtitle = "Report bugs or issues",
                        onClick = { /* TODO: Open bug report form */ }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Legal") {
                    SettingsOption(
                        icon = Icons.Default.PrivacyTip,
                        title = "Privacy Policy",
                        subtitle = "Read our privacy policy",
                        onClick = { /* TODO: Open privacy policy */ }
                    )
                    SettingsOption(
                        icon = Icons.Default.Description,
                        title = "Terms of Service",
                        subtitle = "Read our terms of service",
                        onClick = { /* TODO: Open terms of service */ }
                    )
                }
            }
        }
    }
}

/**
 * Full-screen Linked Devices (like WhatsApp)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkedDevicesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToQRScanner: () -> Unit,
    onNavigateToDeviceLinking: () -> Unit
) {
    val linkedDevices = remember {
        listOf(
            LinkedDevice("Windows", "Last active today at 11:47", "Windows"),
            LinkedDevice("Windows", "Last active 6 October, 01:55", "Windows")
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Linked Devices", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Illustration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Phone
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF4CAF50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.PhoneAndroid,
                            contentDescription = "Phone",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    
                    // Communication bubbles
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF81C784)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF81C784)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Heart",
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                    
                    // Laptop
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF4CAF50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Laptop,
                            contentDescription = "Laptop",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
            
            // Description
            Text(
                text = "You can link other devices to this account. Learn more",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 32.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Link Device Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onNavigateToQRScanner,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Link a device", color = Color.White)
                }
                
                OutlinedButton(
                    onClick = onNavigateToDeviceLinking,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    Icon(Icons.Default.QrCode2, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Show QR Code")
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Device Status
            Text(
                text = "Device Status",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            
            Text(
                text = "Tap a device to log out.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            
            // Linked Devices List
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(linkedDevices) { device ->
                    LinkedDeviceItem(device = device)
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Security Message
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = "Lock",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Your personal messages are end-to-end encrypted on all your devices.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Full-screen Starred Messages
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarredMessagesScreen(
    onNavigateBack: () -> Unit
) {
    val starredMessages = remember { emptyList<String>() } // TODO: Load from database
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Starred Messages", fontWeight = FontWeight.Medium) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        if (starredMessages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.StarBorder,
                        contentDescription = "No Starred Messages",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "No Starred Messages",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Messages you star will appear here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(starredMessages) { message ->
                    // TODO: Implement starred message item
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = message,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

// Helper composables
@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsOption(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SwitchOption(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun LinkedDeviceItem(device: LinkedDevice) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Logout device */ },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Device Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF81C784)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Computer,
                    contentDescription = "Device",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = device.lastActive,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Data classes
data class LinkedDevice(
    val name: String,
    val lastActive: String,
    val type: String
)
