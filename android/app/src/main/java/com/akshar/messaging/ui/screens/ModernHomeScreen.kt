package com.akshar.messaging.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.navigation.NavController
import com.akshar.messaging.ui.navigation.Routes
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernHomeScreen(
    onLogoutClick: () -> Unit,
    onNavigateToContacts: () -> Unit = {},  // ✅ Navigation to contacts
    onNavigateToChat: (String) -> Unit = {},  // ✅ Navigation to chat
    homeViewModel: com.akshar.messaging.ui.home.HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        key = "home_view_model"  // ✅ Stable key to prevent recreation
    ),
    navController: NavController = androidx.navigation.compose.rememberNavController()
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showMenu by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearchDialog by remember { mutableStateOf(false) }
    var showAddContactDialog by remember { mutableStateOf(false) }
    var showCreateGroupDialog by remember { mutableStateOf(false) }
    var showCreateBroadcastDialog by remember { mutableStateOf(false) }
    var showCreateCommunityDialog by remember { mutableStateOf(false) }
    var showCameraOptions by remember { mutableStateOf(false) }  // Camera options dialog
    var showArchivedChats by remember { mutableStateOf(false) }  // Archived chats screen
    
    // Check for external triggers (from ContactsListScreen)
    val context = androidx.compose.ui.platform.LocalContext.current
    val sharedPrefs = context.getSharedPreferences("app_triggers", android.content.Context.MODE_PRIVATE)
    
    androidx.compose.runtime.LaunchedEffect(Unit) {
        // Check if group creation was triggered from ContactsListScreen
        if (sharedPrefs.getBoolean("trigger_group_creation", false)) {
            showCreateGroupDialog = true
            sharedPrefs.edit().putBoolean("trigger_group_creation", false).apply()
        }
        
        // Check if community creation was triggered from ContactsListScreen
        if (sharedPrefs.getBoolean("trigger_community_creation", false)) {
            showCreateCommunityDialog = true
            sharedPrefs.edit().putBoolean("trigger_community_creation", false).apply()
        }
    }
    
    val cameraLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            // Handle captured image
            android.widget.Toast.makeText(context, "Image captured!", android.widget.Toast.LENGTH_SHORT).show()
            // TODO: Upload image or create status
        }
    }
    
    val chats by homeViewModel.chats.collectAsState()
    val currentUser by homeViewModel.currentUser.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val searchResults by homeViewModel.searchResults.collectAsState()
    
    val tabs = listOf(
        TabItem("Chats", Icons.Default.Chat),
        TabItem("Status", Icons.Default.Circle),
        TabItem("Calls", Icons.Default.Call),
        TabItem("Profile", Icons.Default.Person)
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Akshar Messaging",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    // Camera icon (like WhatsApp)
                    IconButton(onClick = { showCameraOptions = true }) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = "Camera")
                    }
                    
                    // Search icon
                    IconButton(onClick = { showSearchDialog = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    
                    // Menu
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                        }
                        
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("New Group") },
                                onClick = { 
                                    showMenu = false
                                    showCreateGroupDialog = true
                                },
                                leadingIcon = { Icon(Icons.Default.GroupAdd, null) }
                            )
                            DropdownMenuItem(
                                text = { Text("New Broadcast") },
                                onClick = { 
                                    showMenu = false
                                    showCreateBroadcastDialog = true
                                },
                                leadingIcon = { Icon(Icons.Default.Campaign, null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Linked Devices") },
                                onClick = { 
                                    showMenu = false
                                    navController.navigate(Routes.LINKED_DEVICES)
                                },
                                leadingIcon = { Icon(Icons.Default.Devices, null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Starred Messages") },
                                onClick = { 
                                    showMenu = false
                                    navController.navigate(Routes.STARRED_MESSAGES)
                                },
                                leadingIcon = { Icon(Icons.Default.Star, null) }
                            )
                            DropdownMenuItem(
                                text = { Text("Settings") },
                                onClick = { 
                                    showMenu = false
                                    navController.navigate(Routes.CHATS_SETTINGS)
                                },
                                leadingIcon = { Icon(Icons.Default.Settings, null) }
                            )
                            Divider()
                            DropdownMenuItem(
                                text = { Text("Logout", color = MaterialTheme.colorScheme.error) },
                                onClick = {
                                    showMenu = false
                                    onLogoutClick()
                                },
                                leadingIcon = { 
                                    Icon(
                                        Icons.Default.Logout, 
                                        null,
                                        tint = MaterialTheme.colorScheme.error
                                    ) 
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                var showChatOptions by remember { mutableStateOf(false) }
                
                Box {
                    FloatingActionButton(
                        onClick = { showChatOptions = !showChatOptions },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ) {
                        Icon(
                            if (showChatOptions) Icons.Default.Close else Icons.Default.Add,
                            contentDescription = "New Chat"
                        )
                    }
                    
                    DropdownMenu(
                        expanded = showChatOptions,
                        onDismissRequest = { showChatOptions = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("New Contact") },
                            onClick = {
                                showChatOptions = false
                                onNavigateToContacts()  // ✅ Navigate to contacts list
                            },
                            leadingIcon = { Icon(Icons.Default.PersonAdd, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("Search & Chat") },
                            onClick = {
                                showChatOptions = false
                                showSearchDialog = true
                            },
                            leadingIcon = { Icon(Icons.Default.Search, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("New Group") },
                            onClick = {
                                showChatOptions = false
                                showCreateGroupDialog = true
                            },
                            leadingIcon = { Icon(Icons.Default.GroupAdd, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("New Broadcast") },
                            onClick = {
                                showChatOptions = false
                                showCreateBroadcastDialog = true
                            },
                            leadingIcon = { Icon(Icons.Default.Campaign, null) }
                        )
                        DropdownMenuItem(
                            text = { Text("New Community") },
                            onClick = {
                                showChatOptions = false
                                showCreateCommunityDialog = true
                            },
                            leadingIcon = { Icon(Icons.Default.Groups, null) }
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                tab.icon, 
                                contentDescription = tab.title,
                                modifier = Modifier.size(24.dp)
                            ) 
                        },
                        label = { Text(tab.title, fontSize = 12.sp) },
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (selectedTab) {
                0 -> ChatsTabContent(chats, isLoading, { homeViewModel.loadChats() }, onNavigateToChat, homeViewModel)
                1 -> StatusTabContent(navController)
                2 -> CallsTabContent()
                3 -> ProfileTabContent(
                    currentUser = currentUser, 
                    onLogoutClick = onLogoutClick, 
                    homeViewModel = homeViewModel,
                    onNavigateToAccount = { navController.navigate(Routes.ACCOUNT_SETTINGS) },
                    onNavigateToChats = { navController.navigate(Routes.CHATS_SETTINGS) },
                    onNavigateToNotifications = { navController.navigate(Routes.NOTIFICATIONS_SETTINGS) },
                    onNavigateToStorage = { navController.navigate(Routes.STORAGE_SETTINGS) },
                    onNavigateToHelp = { navController.navigate(Routes.HELP_CENTER) }
                )
            }
        }
    }
    
    // Add Contact Dialog
    if (showAddContactDialog) {
        val context = androidx.compose.ui.platform.LocalContext.current
        
        AddContactDialog(
            onDismiss = { showAddContactDialog = false },
            onAddContact = { name, phone, email ->
                // Save contact locally
                val contactsManager = com.akshar.messaging.data.local.ContactsManager(context)
                contactsManager.saveContact(name, phone, email)
                
                showAddContactDialog = false
                
                // Show success message
                android.widget.Toast.makeText(
                    context,
                    "Contact '$name' saved successfully! ✓",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
    
    // Search Users Dialog
    if (showSearchDialog) {
        SearchUsersDialog(
            homeViewModel = homeViewModel,
            onDismiss = { 
                showSearchDialog = false
                homeViewModel.clearSearch()
            }
        )
    }
    
    // Camera Options Dialog
    if (showCameraOptions) {
        CameraOptionsDialog(
            onDismiss = { showCameraOptions = false },
            onTakePhoto = {
                cameraLauncher.launch(null)
            },
            onRecordVideo = {
                // TODO: Implement video recording
                android.widget.Toast.makeText(
                    context,
                    "Video recording coming soon!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
    
    // Create Group Dialog
    if (showCreateGroupDialog) {
        // First load contacts for group creation
        androidx.compose.runtime.LaunchedEffect(Unit) {
            homeViewModel.searchUsers("") // Load all users
        }
        
        CreateGroupDialog(
            availableContacts = searchResults,
            onDismiss = { 
                showCreateGroupDialog = false
                homeViewModel.clearSearch()
            },
            onCreate = { groupName, memberIds ->
                // Create group via backend
                homeViewModel.createGroup(groupName, memberIds) { success ->
                    showCreateGroupDialog = false
                    if (success) {
                        homeViewModel.loadChats() // Refresh chat list
                    }
                }
            }
        )
    }
    
    // Create Broadcast Dialog
    if (showCreateBroadcastDialog) {
        CreateBroadcastDialog(
            onDismiss = { showCreateBroadcastDialog = false },
            onCreate = { broadcastName, description ->
                // TODO: Implement broadcast creation via backend
                android.widget.Toast.makeText(
                    context,
                    "Broadcast '$broadcastName' created!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                showCreateBroadcastDialog = false
            }
        )
    }
    
    
    // Create Community Dialog
    if (showCreateCommunityDialog) {
        CreateCommunityDialog(
            onDismiss = { showCreateCommunityDialog = false },
            onCreate = { communityName, description, isPublic ->
                // TODO: Implement community creation via backend
                android.widget.Toast.makeText(
                    context,
                    "Community '$communityName' created!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                showCreateCommunityDialog = false
            }
        )
    }
}

@Composable
fun ChatsTabContent(
    chats: List<com.akshar.messaging.data.models.Chat>?,  // ✅ Allow null
    isLoading: Boolean,
    onRefresh: () -> Unit,
    onNavigateToChat: (String) -> Unit = {},
    homeViewModel: com.akshar.messaging.ui.home.HomeViewModel? = null
) {
    val safeChats = chats ?: emptyList()  // ✅ Default to empty list
    var showArchivedChats by remember { mutableStateOf(false) }
    var archivedChats by remember { mutableStateOf<List<com.akshar.messaging.data.models.Chat>>(emptyList()) }
    val context = androidx.compose.ui.platform.LocalContext.current
    
    // Load archived chats count
    androidx.compose.runtime.LaunchedEffect(Unit) {
        homeViewModel?.loadArchivedChats { archived ->
            archivedChats = archived
        }
    }
    
    val archivedCount = archivedChats.size
    
    if (isLoading && safeChats.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (safeChats.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.Chat,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "No chats yet",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "Tap + to start a new chat",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Archived chats button (like WhatsApp)
            if (archivedCount > 0) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showArchivedChats = true },
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Archive,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                "Archived",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                archivedCount.toString(),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                }
            }
            
            items(safeChats) { chat ->
                RealChatItem(
                    chat = chat,
                    onNavigateToChat = onNavigateToChat,
                    onArchive = {
                        homeViewModel?.archiveChat(chat._id) { success ->
                            if (success) {
                                android.widget.Toast.makeText(
                                    context,
                                    "Chat archived",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
            }
        }
    }
    
    // Archived Chats Screen
    if (showArchivedChats) {
        ArchivedChatsScreen(
            archivedChats = archivedChats,
            onBack = { showArchivedChats = false },
            onUnarchive = { chat ->
                homeViewModel?.unarchiveChat(chat._id) { success ->
                    if (success) {
                        android.widget.Toast.makeText(
                            context,
                            "Chat unarchived",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                        // Reload archived chats
                        homeViewModel?.loadArchivedChats { archived ->
                            archivedChats = archived
                        }
                    }
                }
            },
            onNavigateToChat = onNavigateToChat
        )
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun RealChatItem(
    chat: com.akshar.messaging.data.models.Chat,
    onNavigateToChat: (String) -> Unit = {},
    onArchive: () -> Unit = {}
) {
    val otherUser = chat.participants.firstOrNull()
    val displayName = if (chat.isGroup) {
        chat.name ?: "Group Chat"
    } else {
        otherUser?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown"
    }
    val lastMessageText = chat.lastMessage?.content ?: "No messages yet"
    val time = chat.lastMessage?.createdAt ?: chat.createdAt
    
    var showMenu by remember { mutableStateOf(false) }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { 
                onNavigateToChat(chat._id)
            }
            .combinedClickable(
                onClick = { onNavigateToChat(chat._id) },
                onLongClick = { showMenu = true }
            ),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayName.first().toString().uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                // Online indicator
                if (otherUser?.status == "online") {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color(0xFF25D366))
                            .padding(2.dp)
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
                        text = displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = formatTime(time),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = lastMessageText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
        
        // Context menu for long press
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            DropdownMenuItem(
                text = { Text("Archive") },
                onClick = {
                    showMenu = false
                    onArchive()
                },
                leadingIcon = { Icon(Icons.Default.Archive, null) }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    showMenu = false
                    // TODO: Implement delete
                },
                leadingIcon = { Icon(Icons.Default.Delete, null) }
            )
        }
    }
}

@Composable
fun ModernChatItem(chat: ChatItemData) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Open Chat */ },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = chat.name.first().toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                // Online indicator
                if (chat.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color(0xFF25D366))
                            .padding(2.dp)
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
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    Text(
                        text = chat.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (chat.unreadCount > 0) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.lastMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f),
                        maxLines = 1
                    )
                    
                    if (chat.unreadCount > 0) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = chat.unreadCount.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusTabContent(
    navController: NavController,
    statusViewModel: com.akshar.messaging.ui.status.StatusViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    
    // Simplified Status Tab - Backend is ready, UI will be enhanced later
    val statuses by statusViewModel.statuses.collectAsState()
    val isLoading by statusViewModel.isLoading.collectAsState()
    val error by statusViewModel.error.collectAsState()
    
    // Load statuses with token from TokenManager
    LaunchedEffect(Unit) {
        val token = com.akshar.messaging.utils.TokenManager.getBearerToken(context)
        if (token != null) {
            statusViewModel.loadStatuses(token)
        }
    }

    // Simple Status Tab UI - Backend is fully functional!
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.CameraAlt,
                contentDescription = "Status",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Status Feature",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Backend API Ready ✅",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF25D366),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Logged in as: ${com.akshar.messaging.utils.TokenManager.getFullName(context) ?: "User"}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "${statuses.size} statuses loaded",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (error != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Auth needed: $error",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { navController.navigate(Routes.ADD_STATUS) }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Create Status")
            }
            
            if (isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }
    }
}

// WhatsApp-like My Status Item
@Composable
fun MyStatusItem(
    hasUpdates: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = "https://via.placeholder.com/150"
                    ),
                    contentDescription = "My Profile",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            
            // Add Status Button (Green circle with +)
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF25D366)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Status",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "My Status",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (hasUpdates) "Tap to add status update" else "Tap to add status update",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// WhatsApp-like User Status Item
@Composable
fun UserStatusItem(
    status: UserStatus,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Picture with Status Ring
        Box(
            modifier = Modifier.size(56.dp),
            contentAlignment = Alignment.Center
        ) {
            // Status Ring (if has unseen updates)
            if (status.hasUnseen) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF25D366))
                        .padding(2.dp)
                )
            }
            
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(if (status.hasUnseen) 52.dp else 48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = status.profilePicUrl
                    ),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(if (status.hasUnseen) 48.dp else 44.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = status.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = status.lastUpdate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

data class UserStatus(
    val id: String,
    val name: String,
    val lastUpdate: String,
    val hasUnseen: Boolean,
    val profilePicUrl: String
)

// Helper function to format time ago
private fun formatTimeAgo(createdAt: String): String {
    return try {
        val now = System.currentTimeMillis()
        val created = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.getDefault())
            .parse(createdAt)?.time ?: now
        val diff = now - created
        
        when {
            diff < 60000 -> "Just now"
            diff < 3600000 -> "${diff / 60000}m ago"
            diff < 86400000 -> "${diff / 3600000}h ago"
            else -> "${diff / 86400000}d ago"
        }
    } catch (e: Exception) {
        "Unknown"
    }
}

@Composable
fun CallsTabContent() {
    val context = androidx.compose.ui.platform.LocalContext.current
    
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(8) { index ->
            CallItem(
                name = "Contact ${index + 1}",
                time = if (index < 3) "Today, ${index + 10}:30 AM" else "Yesterday",
                isIncoming = index % 2 == 0,
                isVideoCall = index % 3 == 0,
                onCallClick = { isVideo ->
                    // Start call activity
                    val intent = android.content.Intent(context, com.akshar.messaging.presentation.call.CallActivity::class.java)
                    intent.putExtra("caller_name", "Contact ${index + 1}")
                    intent.putExtra("is_video_call", isVideo)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun CallItem(
    name: String, 
    time: String, 
    isIncoming: Boolean, 
    isVideoCall: Boolean,
    onCallClick: (Boolean) -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Call */ },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = name.first().toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (isIncoming) Icons.Default.CallReceived else Icons.Default.CallMade,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (isIncoming) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            IconButton(onClick = { onCallClick(isVideoCall) }) {
                Icon(
                    if (isVideoCall) Icons.Default.Videocam else Icons.Default.Call,
                    contentDescription = "Call",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ProfileTabContent(
    currentUser: com.akshar.messaging.data.models.User?, 
    onLogoutClick: () -> Unit,
    homeViewModel: com.akshar.messaging.ui.home.HomeViewModel,
    onNavigateToAccount: () -> Unit,
    onNavigateToChats: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToStorage: () -> Unit,
    onNavigateToHelp: () -> Unit
) {
    val userName = currentUser?.let { "${it.firstName} ${it.lastName}" } ?: "Loading..."
    val userEmail = currentUser?.email ?: ""
    val userInitial = currentUser?.firstName?.first()?.toString()?.uppercase() ?: "?"
    val userBio = currentUser?.bio ?: "Hey there! I'm using Akshar Messaging"
    
    var showProfileEditDialog by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }
    
    // Photo picker launcher
    val context = androidx.compose.ui.platform.LocalContext.current
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            // ✅ Upload photo to backend
            homeViewModel.uploadProfilePhoto(it, context) { success ->
                if (success) {
                    android.widget.Toast.makeText(
                        context,
                        "Profile photo updated successfully!",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                } else {
                    android.widget.Toast.makeText(
                        context,
                        "Failed to upload photo",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Profile Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { showPhotoPicker = true },
                    contentAlignment = Alignment.Center
                ) {
                    // Show avatar if available, otherwise show initial
                    if (currentUser?.avatar != null && currentUser.avatar.isNotEmpty()) {
                        androidx.compose.foundation.Image(
                            painter = rememberAsyncImagePainter(currentUser.avatar),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    } else {
                        Text(
                            text = userInitial,
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    // Camera icon overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { showPhotoPicker = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Photo",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Bio section
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = userBio,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = { showProfileEditDialog = true },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
        
        // Profile Options
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            ProfileOption(
                icon = Icons.Default.Person,
                title = "Account",
                subtitle = "Privacy, security, change number",
                onClick = onNavigateToAccount
            )
            
            ProfileOption(
                icon = Icons.Default.Chat,
                title = "Chats",
                subtitle = "Theme, wallpapers, chat history",
                onClick = onNavigateToChats
            )
            
            ProfileOption(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                subtitle = "Message, group & call tones",
                onClick = onNavigateToNotifications
            )
            
            ProfileOption(
                icon = Icons.Default.Storage,
                title = "Storage and data",
                subtitle = "Network usage, auto-download",
                onClick = onNavigateToStorage
            )
            
            ProfileOption(
                icon = Icons.Default.Help,
                title = "Help",
                subtitle = "Help center, contact us, privacy policy",
                onClick = onNavigateToHelp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLogoutClick() },
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Logout,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "Logout",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
    
    // Photo picker trigger
    if (showPhotoPicker) {
        photoPickerLauncher.launch("image/*")
        showPhotoPicker = false
    }
    
    // Profile edit dialog
    if (showProfileEditDialog) {
        val dialogContext = androidx.compose.ui.platform.LocalContext.current
        ProfileEditDialog(
            currentUser = currentUser,
            onDismiss = { showProfileEditDialog = false },
            onSave = { firstName, lastName, bio ->
                homeViewModel.updateProfile(firstName, lastName, bio) { success ->
                    if (success) {
                        android.widget.Toast.makeText(
                            dialogContext,
                            "Profile updated successfully!",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                        showProfileEditDialog = false
                    } else {
                        android.widget.Toast.makeText(
                            dialogContext,
                            "Failed to update profile",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }
    
}

@Composable
fun ProfileOption(icon: ImageVector, title: String, subtitle: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surface
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
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchUsersDialog(
    homeViewModel: com.akshar.messaging.ui.home.HomeViewModel,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by homeViewModel.searchResults.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Start New Chat") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        homeViewModel.searchUsers(it)
                    },
                    placeholder = { Text("Search by name or email") },
                    leadingIcon = { Icon(Icons.Default.Search, null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (searchResults.isEmpty() && searchQuery.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No users found",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else if (searchResults.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Search for users to start chatting",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        items(searchResults) { user ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        homeViewModel.createChat(user.id) { chatId ->
                                            // Successfully created chat
                                            onDismiss()
                                        }
                                    },
                                color = MaterialTheme.colorScheme.surface
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = user.firstName.first().toString().uppercase(),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(12.dp))
                                    
                                    Column {
                                        Text(
                                            text = "${user.firstName} ${user.lastName}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "@${user.username}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusUploadDialog(
    onDismiss: () -> Unit,
    onUpload: (String) -> Unit
) {
    var caption by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create Status") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Image/Video picker placeholder
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { /* Pick image/video */ },
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.AddPhotoAlternate,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Tap to select photo or video",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    placeholder = { Text("Add a caption...") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(onClick = { onUpload(caption) }) {
                Text("Post")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CameraOptionsDialog(
    onDismiss: () -> Unit,
    onTakePhoto: () -> Unit,
    onRecordVideo: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Camera Options") },
        text = {
            Column {
                TextButton(
                    onClick = {
                        onTakePhoto()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.PhotoCamera, null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Take Photo")
                    }
                }
                
                TextButton(
                    onClick = {
                        onRecordVideo()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Videocam, null)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Record Video")
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchivedChatsScreen(
    archivedChats: List<com.akshar.messaging.data.models.Chat>,
    onBack: () -> Unit,
    onUnarchive: (com.akshar.messaging.data.models.Chat) -> Unit,
    onNavigateToChat: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Archived Chats") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (archivedChats.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Archive,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No archived chats",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Long press a chat to archive it",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(archivedChats) { chat ->
                    RealChatItem(
                        chat = chat,
                        onNavigateToChat = onNavigateToChat,
                        onArchive = { onUnarchive(chat) } // Unarchive on action
                    )
                    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
                }
            }
        }
    }
}

// Linked Devices Dialog (moved outside ArchivedChatsScreen, inside ModernHomeScreen scope)
// These will be called from ModernHomeScreen's three dots menu

fun formatTime(isoString: String): String {
    return try {
        // Simple time formatting - you can enhance this
        val parts = isoString.split("T")
        if (parts.size > 1) {
            val timePart = parts[1].split(":") 
            "${timePart[0]}:${timePart[1]}"
        } else {
            "Now"
        }
    } catch (e: Exception) {
        "Now"
    }
}

data class TabItem(val title: String, val icon: ImageVector)

// Profile Edit Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditDialog(
    currentUser: com.akshar.messaging.data.models.User?,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var firstName by remember { mutableStateOf(currentUser?.firstName ?: "") }
    var lastName by remember { mutableStateOf(currentUser?.lastName ?: "") }
    var bio by remember { mutableStateOf(currentUser?.bio ?: "") }
    var isLoading by remember { mutableStateOf(false) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Profile") },
        text = {
            Column {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Bio") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    isLoading = true
                    onSave(firstName, lastName, bio)
                    // onDismiss() will be called after API response
                },
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Linked Devices Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkedDevicesDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Linked Devices") },
        text = {
            Column {
                Text("Your linked devices:")
                Spacer(modifier = Modifier.height(16.dp))
                
                // Current device
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.PhoneAndroid,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("This Device", fontWeight = FontWeight.Medium)
                        Text("Android Phone", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Active", color = Color.Green, fontSize = 12.sp)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Text("No other devices linked", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// Starred Messages Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarredMessagesDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Starred Messages") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Your starred messages:")
                Spacer(modifier = Modifier.height(16.dp))
                
                // Empty state
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No starred messages", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("Tap and hold any message to star it", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// Settings Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Settings") },
        text = {
            Column {
                Text("App Settings:")
                Spacer(modifier = Modifier.height(16.dp))
                
                // Settings options
                SettingsOption(
                    icon = Icons.Default.Notifications,
                    title = "Notifications",
                    subtitle = "Manage your notification preferences"
                )
                
                SettingsOption(
                    icon = Icons.Default.PrivacyTip,
                    title = "Privacy & Security",
                    subtitle = "Control who can see your info"
                )
                
                SettingsOption(
                    icon = Icons.Default.Storage,
                    title = "Storage & Data",
                    subtitle = "Manage your storage usage"
                )
                
                SettingsOption(
                    icon = Icons.Default.Language,
                    title = "Language",
                    subtitle = "English"
                )
                
                SettingsOption(
                    icon = Icons.Default.Help,
                    title = "Help",
                    subtitle = "Get help and support"
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun SettingsOption(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.Medium)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        }
    }
}

data class ChatItemData(
    val name: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val isOnline: Boolean
)

// Account Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Account Settings") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                AccountOption("Privacy", "Control your privacy settings")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Security", "Enable two-factor authentication")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Change Number", "Update your phone number")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Request Account Info", "Get a report of your account info")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Delete My Account", "Permanently delete your account")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun AccountOption(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Implement */ }
            .padding(vertical = 8.dp)
    ) {
        Text(title, fontWeight = FontWeight.Medium)
        Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
    }
}

// Chats Settings Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsSettingsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Chat Settings") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                SwitchOption("Dark Mode", "Use dark theme", true)
                Spacer(modifier = Modifier.height(8.dp))
                SwitchOption("Enter is Send", "Press Enter to send message", false)
                Spacer(modifier = Modifier.height(8.dp))
                SwitchOption("Media Visibility", "Show media in gallery", true)
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Wallpaper", "Change chat wallpaper")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Chat Backup", "Backup your chat history")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Chat History", "Clear or export chat history")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun SwitchOption(title: String, subtitle: String, checked: Boolean) {
    var switchState by remember { mutableStateOf(checked) }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        }
        Switch(
            checked = switchState,
            onCheckedChange = { switchState = it }
        )
    }
}

// Notifications Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Notification Settings") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                SwitchOption("Conversation Tones", "Play sounds for incoming and outgoing messages", true)
                Spacer(modifier = Modifier.height(8.dp))
                SwitchOption("Vibrate", "Vibrate on new messages", true)
                Spacer(modifier = Modifier.height(8.dp))
                SwitchOption("Popup Notification", "Show notifications as popup", false)
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Message Notifications", "Customize message notification tone")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Group Notifications", "Customize group notification tone")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Call Ringtone", "Choose call ringtone")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// Storage Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageDialog(onDismiss: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val storageInfo = remember {
        com.akshar.messaging.utils.StorageUtil.getAppStorageUsage(context)
    }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Storage & Data") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Text("Storage Usage", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("App: ${com.akshar.messaging.utils.StorageUtil.formatSize(storageInfo.appSize)}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Text("Media: ${com.akshar.messaging.utils.StorageUtil.formatSize(storageInfo.mediaSize)}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                Text("Cache: ${com.akshar.messaging.utils.StorageUtil.formatSize(storageInfo.cacheSize)}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                
                Spacer(modifier = Modifier.height(16.dp))
                
                SwitchOption("Auto-download Media", "Automatically download media when on WiFi", true)
                Spacer(modifier = Modifier.height(8.dp))
                SwitchOption("Download over Mobile Data", "Allow downloads on mobile data", false)
                Spacer(modifier = Modifier.height(8.dp))
                
                // Clear cache button
                androidx.compose.material3.Button(
                    onClick = {
                        val success = com.akshar.messaging.utils.StorageUtil.clearCache(context)
                        android.widget.Toast.makeText(
                            context,
                            if (success) "Cache cleared successfully!" else "Failed to clear cache",
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                        onDismiss()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Cache (${com.akshar.messaging.utils.StorageUtil.formatSize(storageInfo.cacheSize)})")
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Network Usage", "View data usage statistics")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// Help Dialog
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Help Center") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                AccountOption("FAQ", "Frequently asked questions")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Contact Us", "Get in touch with support")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Privacy Policy", "Read our privacy policy")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("Terms of Service", "Read terms and conditions")
                Spacer(modifier = Modifier.height(8.dp))
                AccountOption("App Info", "Version 1.0.0 (Beta)")
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    "Akshar Messaging v1.0.0",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}


