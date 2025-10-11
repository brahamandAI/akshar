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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupDialog(
    availableContacts: List<com.akshar.messaging.data.models.User>,
    onDismiss: () -> Unit,
    onCreate: (String, List<String>) -> Unit
) {
    var groupName by remember { mutableStateOf("") }
    var selectedContacts by remember { mutableStateOf(setOf<String>()) }
    var currentStep by remember { mutableStateOf(1) } // 1 = select contacts, 2 = group info
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(
                if (currentStep == 1) "Select Contacts" else "Group Info"
            ) 
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (currentStep == 1) {
                    // Step 1: Select contacts
                    Text(
                        "Select at least 2 contacts for group",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    if (selectedContacts.isNotEmpty()) {
                        Text(
                            "${selectedContacts.size} selected",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    ) {
                        items(availableContacts) { contact ->
                            val isSelected = selectedContacts.contains(contact.id)
                            
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedContacts = if (isSelected) {
                                            selectedContacts - contact.id
                                        } else {
                                            selectedContacts + contact.id
                                        }
                                    },
                                color = if (isSelected) 
                                    MaterialTheme.colorScheme.primaryContainer 
                                else 
                                    MaterialTheme.colorScheme.surface
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
                                            text = contact.firstName.first().toString().uppercase(),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    
                                    Spacer(modifier = Modifier.width(12.dp))
                                    
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${contact.firstName} ${contact.lastName}",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "@${contact.username}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    
                                    if (isSelected) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Step 2: Group info
                    OutlinedTextField(
                        value = groupName,
                        onValueChange = { groupName = it },
                        label = { Text("Group Name *") },
                        leadingIcon = { Icon(Icons.Default.Groups, null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("Family, Friends, Work...") }
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        "Group with ${selectedContacts.size} members",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (currentStep == 1 && selectedContacts.size >= 2) {
                        currentStep = 2
                    } else if (currentStep == 2 && groupName.isNotBlank()) {
                        onCreate(groupName, selectedContacts.toList())
                    }
                },
                enabled = if (currentStep == 1) selectedContacts.size >= 2 else groupName.isNotBlank()
            ) {
                Text(if (currentStep == 1) "Next" else "Create Group")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                if (currentStep == 2) {
                    currentStep = 1
                } else {
                    onDismiss()
                }
            }) {
                Text(if (currentStep == 2) "Back" else "Cancel")
            }
        }
    )
}

