package com.akshar.messaging.data.webrtc

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.webrtc.*

class WebRTCManager(private val context: Context) {
    
    private var peerConnectionFactory: PeerConnectionFactory? = null
    private var peerConnection: PeerConnection? = null
    private var localVideoTrack: VideoTrack? = null
    private var localAudioTrack: AudioTrack? = null
    private var videoCapturer: VideoCapturer? = null
    
    // Call state
    private val _callState = MutableStateFlow<CallState>(CallState.Idle)
    val callState: StateFlow<CallState> = _callState.asStateFlow()
    
    // Remote stream
    private val _remoteVideoTrack = MutableStateFlow<VideoTrack?>(null)
    val remoteVideoTrack: StateFlow<VideoTrack?> = _remoteVideoTrack.asStateFlow()
    
    fun initialize() {
        try {
            // Initialize PeerConnectionFactory
            val options = PeerConnectionFactory.InitializationOptions.builder(context)
                .setEnableInternalTracer(true)
                .createInitializationOptions()
            
            PeerConnectionFactory.initialize(options)
            
            val encoderFactory = DefaultVideoEncoderFactory(
                EglBase.create().eglBaseContext,
                true,
                true
            )
            
            val decoderFactory = DefaultVideoDecoderFactory(EglBase.create().eglBaseContext)
            
            peerConnectionFactory = PeerConnectionFactory.builder()
                .setVideoEncoderFactory(encoderFactory)
                .setVideoDecoderFactory(decoderFactory)
                .setOptions(PeerConnectionFactory.Options())
                .createPeerConnectionFactory()
            
            Log.d("WebRTC", "PeerConnectionFactory initialized")
        } catch (e: Exception) {
            Log.e("WebRTC", "Error initializing WebRTC", e)
        }
    }
    
    fun startCall(isVideoCall: Boolean) {
        try {
            _callState.value = CallState.Connecting
            
            // Create peer connection
            val rtcConfig = PeerConnection.RTCConfiguration(
                listOf(
                    PeerConnection.IceServer.builder("stun:stun.l.google.com:19302").createIceServer()
                )
            )
            
            peerConnection = peerConnectionFactory?.createPeerConnection(
                rtcConfig,
                object : PeerConnection.Observer {
                    override fun onIceCandidate(candidate: IceCandidate?) {
                        candidate?.let {
                            // Send candidate via signaling server
                            Log.d("WebRTC", "ICE candidate: ${it.sdp}")
                        }
                    }
                    
                    override fun onAddStream(stream: MediaStream?) {
                        stream?.videoTracks?.firstOrNull()?.let {
                            _remoteVideoTrack.value = it
                        }
                    }
                    
                    override fun onIceConnectionChange(state: PeerConnection.IceConnectionState?) {
                        when (state) {
                            PeerConnection.IceConnectionState.CONNECTED -> {
                                _callState.value = CallState.Connected
                            }
                            PeerConnection.IceConnectionState.DISCONNECTED -> {
                                _callState.value = CallState.Disconnected
                            }
                            PeerConnection.IceConnectionState.FAILED -> {
                                _callState.value = CallState.Failed
                            }
                            else -> {}
                        }
                    }
                    
                    override fun onSignalingChange(state: PeerConnection.SignalingState?) {}
                    override fun onIceConnectionReceivingChange(receiving: Boolean) {}
                    override fun onIceGatheringChange(state: PeerConnection.IceGatheringState?) {}
                    override fun onRemoveStream(stream: MediaStream?) {}
                    override fun onDataChannel(channel: DataChannel?) {}
                    override fun onRenegotiationNeeded() {}
                    override fun onAddTrack(receiver: RtpReceiver?, streams: Array<out MediaStream>?) {}
                    override fun onIceCandidatesRemoved(candidates: Array<out IceCandidate>?) {
                        Log.d("WebRTC", "ICE candidates removed")
                    }
                }
            )
            
            // Create local media stream
            createLocalMediaStream(isVideoCall)
            
            Log.d("WebRTC", "Call started")
        } catch (e: Exception) {
            Log.e("WebRTC", "Error starting call", e)
            _callState.value = CallState.Failed
        }
    }
    
    private fun createLocalMediaStream(includeVideo: Boolean) {
        // Audio track
        val audioSource = peerConnectionFactory?.createAudioSource(MediaConstraints())
        localAudioTrack = peerConnectionFactory?.createAudioTrack("audio_track", audioSource)
        
        // Video track (if video call)
        if (includeVideo) {
            val videoSource = peerConnectionFactory?.createVideoSource(false)
            localVideoTrack = peerConnectionFactory?.createVideoTrack("video_track", videoSource)
        }
        
        // Add tracks to peer connection
        val mediaStream = peerConnectionFactory?.createLocalMediaStream("local_stream")
        localAudioTrack?.let { mediaStream?.addTrack(it) }
        localVideoTrack?.let { mediaStream?.addTrack(it) }
        
        mediaStream?.let { peerConnection?.addStream(it) }
    }
    
    fun endCall() {
        try {
            localVideoTrack?.dispose()
            localAudioTrack?.dispose()
            videoCapturer?.dispose()
            peerConnection?.close()
            
            localVideoTrack = null
            localAudioTrack = null
            videoCapturer = null
            peerConnection = null
            
            _callState.value = CallState.Idle
            _remoteVideoTrack.value = null
            
            Log.d("WebRTC", "Call ended")
        } catch (e: Exception) {
            Log.e("WebRTC", "Error ending call", e)
        }
    }
    
    fun toggleMute() {
        localAudioTrack?.setEnabled(localAudioTrack?.enabled() == false)
    }
    
    fun toggleVideo() {
        localVideoTrack?.setEnabled(localVideoTrack?.enabled() == false)
    }
    
    fun switchCamera() {
        (videoCapturer as? CameraVideoCapturer)?.switchCamera(null)
    }
    
    fun cleanup() {
        endCall()
        peerConnectionFactory?.dispose()
        peerConnectionFactory = null
    }
}

// Call states
sealed class CallState {
    object Idle : CallState()
    object Connecting : CallState()
    object Ringing : CallState()
    object Connected : CallState()
    object Disconnected : CallState()
    object Failed : CallState()
}
