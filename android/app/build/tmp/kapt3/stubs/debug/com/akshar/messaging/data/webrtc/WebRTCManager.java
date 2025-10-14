package com.akshar.messaging.data.webrtc;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0006\u0010\u001e\u001a\u00020\u001aJ\u0006\u0010\u001f\u001a\u00020\u001aJ\u000e\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u001dJ\u0006\u0010\"\u001a\u00020\u001aJ\u0006\u0010#\u001a\u00020\u001aJ\u0006\u0010$\u001a\u00020\u001aR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0015\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\rR\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/akshar/messaging/data/webrtc/WebRTCManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_callState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/akshar/messaging/data/webrtc/CallState;", "_remoteVideoTrack", "Lorg/webrtc/VideoTrack;", "callState", "Lkotlinx/coroutines/flow/StateFlow;", "getCallState", "()Lkotlinx/coroutines/flow/StateFlow;", "localAudioTrack", "Lorg/webrtc/AudioTrack;", "localVideoTrack", "peerConnection", "Lorg/webrtc/PeerConnection;", "peerConnectionFactory", "Lorg/webrtc/PeerConnectionFactory;", "remoteVideoTrack", "getRemoteVideoTrack", "videoCapturer", "Lorg/webrtc/VideoCapturer;", "cleanup", "", "createLocalMediaStream", "includeVideo", "", "endCall", "initialize", "startCall", "isVideoCall", "switchCamera", "toggleMute", "toggleVideo", "app_debug"})
public final class WebRTCManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable
    private org.webrtc.PeerConnectionFactory peerConnectionFactory;
    @org.jetbrains.annotations.Nullable
    private org.webrtc.PeerConnection peerConnection;
    @org.jetbrains.annotations.Nullable
    private org.webrtc.VideoTrack localVideoTrack;
    @org.jetbrains.annotations.Nullable
    private org.webrtc.AudioTrack localAudioTrack;
    @org.jetbrains.annotations.Nullable
    private org.webrtc.VideoCapturer videoCapturer;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<com.akshar.messaging.data.webrtc.CallState> _callState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.akshar.messaging.data.webrtc.CallState> callState = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<org.webrtc.VideoTrack> _remoteVideoTrack = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<org.webrtc.VideoTrack> remoteVideoTrack = null;
    
    public WebRTCManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.akshar.messaging.data.webrtc.CallState> getCallState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<org.webrtc.VideoTrack> getRemoteVideoTrack() {
        return null;
    }
    
    public final void initialize() {
    }
    
    public final void startCall(boolean isVideoCall) {
    }
    
    private final void createLocalMediaStream(boolean includeVideo) {
    }
    
    public final void endCall() {
    }
    
    public final void toggleMute() {
    }
    
    public final void toggleVideo() {
    }
    
    public final void switchCamera() {
    }
    
    public final void cleanup() {
    }
}