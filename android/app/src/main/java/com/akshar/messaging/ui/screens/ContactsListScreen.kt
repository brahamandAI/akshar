package com.akshar.messaging.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

data class ContactItem(
    val name: String,
    val phone: String,
    val email: String = "",
    val isRegistered: Boolean = false,  // Is user on our app?
    val userId: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsListScreen(
    onBackClick: () -> Unit,
    onContactClick: (ContactItem) -> Unit,
    onAddNewContact: () -> Unit,
    onCreateGroup: () -> Unit = {},
    onCreateCommunity: () -> Unit = {}
) {
    var showAddContactDialog by remember { mutableStateOf(false) }
    var showSearchDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val context = androidx.compose.ui.platform.LocalContext.current
    val contactsManager = com.akshar.messaging.data.local.ContactsManager(context)
    
    // HomeViewModel for backend search
    val homeViewModel = androidx.lifecycle.viewmodel.compose.viewModel<com.akshar.messaging.ui.home.HomeViewModel>(
        key = "contacts_home_vm"
    )
    val backendSearchResults by homeViewModel.searchResults.collectAsState()
    
    // Load saved contacts
    var savedContacts by remember {
        mutableStateOf(contactsManager.getAllContacts())
    }
    
    // Refresh contacts when screen appears
    androidx.compose.runtime.LaunchedEffect(Unit) {
        savedContacts = contactsManager.getAllContacts()
        
        // Add test users if no contacts exist (for single device testing)
        if (savedContacts.isEmpty()) {
            val testContacts = com.akshar.messaging.utils.TestUserHelper.createTestContacts()
            testContacts.forEach { testContact ->
                contactsManager.saveContact(testContact.name, testContact.phoneNumber, testContact.email)
            }
            savedContacts = contactsManager.getAllContacts()
            android.util.Log.d("ContactsListScreen", "Added ${testContacts.size} test contacts for single device testing")
        }
        
        android.util.Log.d("ContactsListScreen", "Screen loaded with ${savedContacts.size} contacts")
        savedContacts.forEach { contact ->
            android.util.Log.d("ContactsListScreen", "Contact: ${contact.name} - ${contact.phoneNumber}")
        }
    }
    
    // Convert to ContactItems
    val contacts = remember(savedContacts) {
        savedContacts.map { contact ->
            ContactItem(
                name = contact.name,
                phone = contact.phoneNumber ?: "",
                email = contact.email ?: "",
                isRegistered = com.akshar.messaging.utils.TestUserHelper.isTestUser(contact.phoneNumber ?: ""),
                userId = com.akshar.messaging.utils.TestUserHelper.getTestUserId(contact.phoneNumber ?: "")
            )
        }
    }
    
    // Filtered contacts based on search query with debouncing + backend search
    var displayedContacts by remember { mutableStateOf(contacts) }
    var isSearchingBackend by remember { mutableStateOf(false) }
    
    LaunchedEffect(searchQuery) {
        val query = searchQuery.trim()
        if (query.isEmpty()) {
            displayedContacts = contacts
            isSearchingBackend = false
        } else {
            delay(300) // debounce
            
            // First, filter local contacts
            val localFiltered = contacts.filter {
                it.name.contains(query, ignoreCase = true) || 
                it.phone.contains(query, ignoreCase = true)
            }
            
            // Then, search backend for registered users
            isSearchingBackend = true
            homeViewModel.searchUsers(query)
        }
    }
    
    // Combine local + backend results
    LaunchedEffect(backendSearchResults, isSearchingBackend) {
        if (isSearchingBackend) {
            val query = searchQuery.trim()
            
            // Local filtered contacts
            val localFiltered = contacts.filter {
                it.name.contains(query, ignoreCase = true) || 
                it.phone.contains(query, ignoreCase = true)
            }
            
            // Backend users as ContactItems
            val backendContacts = backendSearchResults.map { user ->
                ContactItem(
                    name = "${user.firstName} ${user.lastName}".trim(),
                    phone = user.username,
                    email = user.email ?: "",
                    isRegistered = true,
                    userId = user.id
                )
            }
            
            // Merge: show backend results first, then local
            displayedContacts = (backendContacts + localFiltered).distinctBy { it.phone }
            isSearchingBackend = false
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Select Contact")
                        Text(
                            text = "${displayedContacts.size} contacts",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    // Search icon
                    IconButton(onClick = { showSearchDialog = true }) {
                        Icon(Icons.Default.Search, "Search")
                    }
                    IconButton(onClick = { /* TODO: Menu */ }) {
                        Icon(Icons.Default.MoreVert, "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddContactDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.PersonAdd, "Add Contact")
            }
        }
    ) { paddingValues ->
        if (displayedContacts.isEmpty() && searchQuery.isNotEmpty()) {
            // No search results
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
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No contacts found",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Try a different search term",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
            }
        } else if (contacts.isEmpty()) {
            // Empty state
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
                        imageVector = Icons.Default.ContactPage,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No contacts yet",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap + to add a new contact",
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
                // WhatsApp-style action buttons
                item {
                    Column {
                        // New Group
                        ActionButton(
                            icon = Icons.Default.GroupAdd,
                            text = "New group",
                            onClick = { onCreateGroup() }
                        )
                        
                        // New Contact
                        ActionButton(
                            icon = Icons.Default.PersonAdd,
                            text = "New contact",
                            onClick = { showAddContactDialog = true },
                            trailingIcon = Icons.Default.QrCode
                        )
                        
                        // New Community
                        ActionButton(
                            icon = Icons.Default.Groups,
                            text = "New community",
                            onClick = { onCreateCommunity() }
                        )
                    }
                    
                    // Divider
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        thickness = 8.dp,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
                
                // Header for contacts
                item {
                    Text(
                        text = "Contacts on Akshar Messaging",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                // Contact list
                items(displayedContacts) { contact ->
                    ContactListItem(
                        contact = contact,
                        onClick = { onContactClick(contact) }
                    )
                }
            }
        }
    }
    
    // Add Contact Dialog
    if (showAddContactDialog) {
        AddContactDialog(
            onDismiss = { showAddContactDialog = false },
            onAddContact = { name, phone, email ->
                // Save the contact
                contactsManager.saveContact(name, phone, email)
                showAddContactDialog = false
                // Refresh contacts immediately
                savedContacts = contactsManager.getAllContacts()
                
                // Debug info
                android.util.Log.d("ContactsListScreen", "Contact saved: $name")
                android.util.Log.d("ContactsListScreen", "Total contacts now: ${savedContacts.size}")
                
                // Show success message
                android.widget.Toast.makeText(
                    context,
                    "Contact '$name' saved! Total: ${savedContacts.size}",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
    
    // Search Dialog
    if (showSearchDialog) {
        SearchContactsDialog(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            displayedContacts = displayedContacts,
            isSearchingBackend = isSearchingBackend,
            onDismiss = { showSearchDialog = false },
            onContactClick = { contact ->
                onContactClick(contact)
                showSearchDialog = false
            }
        )
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Green circular icon (like WhatsApp)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF25D366)), // WhatsApp green
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Text
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            
            // Trailing icon (like QR code)
            if (trailingIcon != null) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
    Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
}

@Composable
fun ContactListItem(
    contact: ContactItem,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (contact.isRegistered) 
                            MaterialTheme.colorScheme.primaryContainer
                        else 
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.firstOrNull()?.uppercase() ?: "?",
                    style = MaterialTheme.typography.titleLarge,
                    color = if (contact.isRegistered)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Contact info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (contact.isRegistered) 
                        "Available for chat"
                    else 
                        contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = if (contact.isRegistered) 0.8f else 0.6f
                    )
                )
            }
            
            // Message icon (always show - will check on click)
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Message,
                    contentDescription = "Start Chat",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
    Divider(
        modifier = Modifier.padding(start = 80.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContactsDialog(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    displayedContacts: List<ContactItem>,
    isSearchingBackend: Boolean,
    onDismiss: () -> Unit,
    onContactClick: (ContactItem) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Search Contacts") },
        text = {
            Column {
                // Search TextField
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search by name or phone") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Search, "Search")
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onSearchQueryChange("") }) {
                                Icon(Icons.Default.Clear, "Clear")
                            }
                        }
                    }
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Search results
                if (isSearchingBackend) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (displayedContacts.isEmpty() && searchQuery.isNotEmpty()) {
                    Text(
                        text = "No contacts found",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 300.dp)
                    ) {
                        items(displayedContacts) { contact ->
                            ContactListItem(
                                contact = contact,
                                onClick = { onContactClick(contact) }
                            )
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

