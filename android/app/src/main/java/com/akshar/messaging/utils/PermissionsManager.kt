package com.akshar.messaging.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class PermissionsManager(private val activity: ComponentActivity) {
    
    private var onPermissionsGranted: (() -> Unit)? = null
    private var onPermissionsDenied: (() -> Unit)? = null
    
    private val permissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        
        if (allGranted) {
            onPermissionsGranted?.invoke()
        } else {
            onPermissionsDenied?.invoke()
        }
    }
    
    fun requestCameraPermissions(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        onPermissionsGranted = onGranted
        onPermissionsDenied = onDenied
        
        val permissions = mutableListOf(Manifest.permission.CAMERA)
        
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        
        if (hasPermissions(activity, permissions)) {
            onGranted()
        } else {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }
    
    fun requestAudioPermissions(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        onPermissionsGranted = onGranted
        onPermissionsDenied = onDenied
        
        val permissions = listOf(Manifest.permission.RECORD_AUDIO)
        
        if (hasPermissions(activity, permissions)) {
            onGranted()
        } else {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }
    
    fun requestCallPermissions(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        onPermissionsGranted = onGranted
        onPermissionsDenied = onDenied
        
        val permissions = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
        
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        
        if (hasPermissions(activity, permissions)) {
            onGranted()
        } else {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }
    
    fun requestStoragePermissions(
        onGranted: () -> Unit,
        onDenied: () -> Unit
    ) {
        onPermissionsGranted = onGranted
        onPermissionsDenied = onDenied
        
        val permissions = mutableListOf<String>()
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.addAll(
                listOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.READ_MEDIA_VIDEO,
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            )
        } else {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
        
        if (hasPermissions(activity, permissions)) {
            onGranted()
        } else {
            permissionLauncher.launch(permissions.toTypedArray())
        }
    }
    
    private fun hasPermissions(context: Context, permissions: List<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    companion object {
        fun hasCameraPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        }
        
        fun hasAudioPermission(context: Context): Boolean {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        }
        
        fun hasStoragePermission(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
    }
}

