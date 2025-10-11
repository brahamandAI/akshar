package com.akshar.messaging.ui.call

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akshar.messaging.data.webrtc.CallState
import com.akshar.messaging.data.webrtc.WebRTCManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.webrtc.VideoTrack

class CallViewModel(application: Application) : AndroidViewModel(application) {
    
    private val webRTCManager = WebRTCManager(application.applicationContext)
    
    // Call state
    private val _callState = MutableStateFlow<CallState>(CallState.Idle)
    val callState: StateFlow<CallState> = _callState.asStateFlow()
    
    // Remote video track
    private val _remoteVideoTrack = MutableStateFlow<VideoTrack?>(null)
    val remoteVideoTrack: StateFlow<VideoTrack?> = _remoteVideoTrack.asStateFlow()
    
    // Mute/Video states
    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()
    
    private val _isVideoEnabled = MutableStateFlow(true)
    val isVideoEnabled: StateFlow<Boolean> = _isVideoEnabled.asStateFlow()
    
    // Call info
    private val _callerName = MutableStateFlow("")
    val callerName: StateFlow<String> = _callerName.asStateFlow()
    
    private val _callDuration = MutableStateFlow(0L)
    val callDuration: StateFlow<Long> = _callDuration.asStateFlow()
    
    init {
        webRTCManager.initialize()
        observeCallState()
        observeRemoteVideo()
    }
    
    private fun observeCallState() {
        viewModelScope.launch {
            webRTCManager.callState.collect { state ->
                _callState.value = state
            }
        }
    }
    
    private fun observeRemoteVideo() {
        viewModelScope.launch {
            webRTCManager.remoteVideoTrack.collect { track: VideoTrack? ->
                _remoteVideoTrack.value = track
            }
        }
    }
    
    fun startVoiceCall(userId: String, userName: String) {
        _callerName.value = userName
        _isVideoEnabled.value = false
        webRTCManager.startCall(isVideoCall = false)
    }
    
    fun startVideoCall(userId: String, userName: String) {
        _callerName.value = userName
        _isVideoEnabled.value = true
        webRTCManager.startCall(isVideoCall = true)
    }
    
    fun endCall() {
        webRTCManager.endCall()
        _callerName.value = ""
        _callDuration.value = 0
    }
    
    fun toggleMute() {
        webRTCManager.toggleMute()
        _isMuted.value = !_isMuted.value
    }
    
    fun toggleVideo() {
        webRTCManager.toggleVideo()
        _isVideoEnabled.value = !_isVideoEnabled.value
    }
    
    fun switchCamera() {
        webRTCManager.switchCamera()
    }
    
    override fun onCleared() {
        super.onCleared()
        webRTCManager.cleanup()
    }
}

