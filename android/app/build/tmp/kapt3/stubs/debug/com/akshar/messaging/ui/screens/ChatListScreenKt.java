package com.akshar.messaging.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001aV\u0010\u0006\u001a\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0010"}, d2 = {"ChatListItem", "", "chat", "Lcom/akshar/messaging/data/models/Chat;", "onClick", "Lkotlin/Function0;", "ChatListScreen", "chats", "", "isLoading", "", "isConnected", "onChatClick", "Lkotlin/Function1;", "onLogoutClick", "onRefresh", "app_debug"})
public final class ChatListScreenKt {
    
    @androidx.compose.runtime.Composable
    public static final void ChatListScreen(@org.jetbrains.annotations.NotNull
    java.util.List<com.akshar.messaging.data.models.Chat> chats, boolean isLoading, boolean isConnected, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.akshar.messaging.data.models.Chat, kotlin.Unit> onChatClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onLogoutClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ChatListItem(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.Chat chat, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}