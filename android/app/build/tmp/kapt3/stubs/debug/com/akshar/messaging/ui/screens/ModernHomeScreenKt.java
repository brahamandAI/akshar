package com.akshar.messaging.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000r\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0018\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007\u001aL\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a>\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a\b\u0010\u0017\u001a\u00020\u0001H\u0007\u001a2\u0010\u0018\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0016\u0010\u001b\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001aP\u0010\u001c\u001a\u00020\u00012\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n2\u0006\u0010\u001e\u001a\u00020\u00142\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u000e2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010!H\u0007\u001a\u0016\u0010\"\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0016\u0010#\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0010\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&H\u0007\u001aP\u0010\'\u001a\u00020\u00012\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u000e2\b\b\u0002\u0010 \u001a\u00020!2\b\b\u0002\u0010*\u001a\u00020+H\u0007\u001a\u001e\u0010,\u001a\u00020\u00012\u0006\u0010-\u001a\u00020\u00142\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0016\u0010/\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a@\u00100\u001a\u00020\u00012\b\u00101\u001a\u0004\u0018\u0001022\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u001e\u00103\u001a\u001a\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000104H\u0007\u001a.\u00105\u001a\u00020\u00012\u0006\u00106\u001a\u0002072\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001an\u00108\u001a\u00020\u00012\b\u00101\u001a\u0004\u0018\u0001022\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0006\u0010 \u001a\u00020!2\f\u00109\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a6\u0010>\u001a\u00020\u00012\u0006\u0010%\u001a\u00020\u000b2\u0014\b\u0002\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u000e2\u000e\b\u0002\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u001e\u0010@\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0016\u0010A\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a \u0010B\u001a\u00020\u00012\u0006\u00106\u001a\u0002072\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007\u001a\u0016\u0010C\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u001a\u0010D\u001a\u00020\u00012\u0006\u0010*\u001a\u00020+2\b\b\u0002\u0010E\u001a\u00020FH\u0007\u001a*\u0010G\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a\u0016\u0010I\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a \u0010J\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010K\u001a\u00020\u0014H\u0007\u001a\u001e\u0010L\u001a\u00020\u00012\u0006\u0010M\u001a\u00020N2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u000e\u0010O\u001a\u00020\u00062\u0006\u0010P\u001a\u00020\u0006\u001a\u0010\u0010Q\u001a\u00020\u00062\u0006\u0010R\u001a\u00020\u0006H\u0002\u00a8\u0006S"}, d2 = {"AccountDialog", "", "onDismiss", "Lkotlin/Function0;", "AccountOption", "title", "", "subtitle", "ArchivedChatsScreen", "archivedChats", "", "Lcom/akshar/messaging/data/models/Chat;", "onBack", "onUnarchive", "Lkotlin/Function1;", "onNavigateToChat", "CallItem", "name", "time", "isIncoming", "", "isVideoCall", "onCallClick", "CallsTabContent", "CameraOptionsDialog", "onTakePhoto", "onRecordVideo", "ChatsSettingsDialog", "ChatsTabContent", "chats", "isLoading", "onRefresh", "homeViewModel", "Lcom/akshar/messaging/ui/home/HomeViewModel;", "HelpDialog", "LinkedDevicesDialog", "ModernChatItem", "chat", "Lcom/akshar/messaging/ui/screens/ChatItemData;", "ModernHomeScreen", "onLogoutClick", "onNavigateToContacts", "navController", "Landroidx/navigation/NavController;", "MyStatusItem", "hasUpdates", "onClick", "NotificationsDialog", "ProfileEditDialog", "currentUser", "Lcom/akshar/messaging/data/models/User;", "onSave", "Lkotlin/Function3;", "ProfileOption", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "ProfileTabContent", "onNavigateToAccount", "onNavigateToChats", "onNavigateToNotifications", "onNavigateToStorage", "onNavigateToHelp", "RealChatItem", "onArchive", "SearchUsersDialog", "SettingsDialog", "SettingsOption", "StarredMessagesDialog", "StatusTabContent", "statusViewModel", "Lcom/akshar/messaging/ui/status/StatusViewModel;", "StatusUploadDialog", "onUpload", "StorageDialog", "SwitchOption", "checked", "UserStatusItem", "status", "Lcom/akshar/messaging/ui/screens/UserStatus;", "formatTime", "isoString", "formatTimeAgo", "createdAt", "app_debug"})
public final class ModernHomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ModernHomeScreen(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogoutClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToContacts, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToChat, @org.jetbrains.annotations.NotNull
    com.akshar.messaging.ui.home.HomeViewModel homeViewModel, @org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ChatsTabContent(@org.jetbrains.annotations.Nullable
    java.util.List<com.akshar.messaging.data.models.Chat> chats, boolean isLoading, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToChat, @org.jetbrains.annotations.Nullable
    com.akshar.messaging.ui.home.HomeViewModel homeViewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable
    public static final void RealChatItem(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.Chat chat, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToChat, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onArchive) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ModernChatItem(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.ui.screens.ChatItemData chat) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void StatusTabContent(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.akshar.messaging.ui.status.StatusViewModel statusViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void MyStatusItem(boolean hasUpdates, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void UserStatusItem(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.ui.screens.UserStatus status, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    private static final java.lang.String formatTimeAgo(java.lang.String createdAt) {
        return null;
    }
    
    @androidx.compose.runtime.Composable
    public static final void CallsTabContent() {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CallItem(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String time, boolean isIncoming, boolean isVideoCall, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCallClick) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ProfileTabContent(@org.jetbrains.annotations.Nullable
    com.akshar.messaging.data.models.User currentUser, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogoutClick, @org.jetbrains.annotations.NotNull
    com.akshar.messaging.ui.home.HomeViewModel homeViewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToAccount, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToChats, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToNotifications, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToStorage, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToHelp) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ProfileOption(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void SearchUsersDialog(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.ui.home.HomeViewModel homeViewModel, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void StatusUploadDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onUpload) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CameraOptionsDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onTakePhoto, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRecordVideo) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ArchivedChatsScreen(@org.jetbrains.annotations.NotNull
    java.util.List<com.akshar.messaging.data.models.Chat> archivedChats, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.akshar.messaging.data.models.Chat, kotlin.Unit> onUnarchive, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToChat) {
    }
    
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String formatTime(@org.jetbrains.annotations.NotNull
    java.lang.String isoString) {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ProfileEditDialog(@org.jetbrains.annotations.Nullable
    com.akshar.messaging.data.models.User currentUser, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void LinkedDevicesDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void StarredMessagesDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void SettingsDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SettingsOption(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AccountDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AccountOption(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ChatsSettingsDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void SwitchOption(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String subtitle, boolean checked) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void NotificationsDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void StorageDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void HelpDialog(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
}