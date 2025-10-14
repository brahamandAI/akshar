package com.akshar.messaging.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJ\"\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\fJ\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0015\u001a\u00020\fJ*\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\b\u0010\u0012\u001a\u0004\u0018\u00010\f2\b\u0010\u0013\u001a\u0004\u0018\u00010\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/akshar/messaging/data/local/ContactsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "gson", "Lcom/google/gson/Gson;", "prefs", "Landroid/content/SharedPreferences;", "deleteContact", "", "contactId", "", "getAllContacts", "", "Lcom/akshar/messaging/data/local/LocalContact;", "saveContact", "name", "phoneNumber", "email", "searchContacts", "query", "updateContact", "", "app_debug"})
public final class ContactsManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    
    public ContactsManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.akshar.messaging.data.local.LocalContact saveContact(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable
    java.lang.String email) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.akshar.messaging.data.local.LocalContact> getAllContacts() {
        return null;
    }
    
    public final void deleteContact(@org.jetbrains.annotations.NotNull
    java.lang.String contactId) {
    }
    
    public final boolean updateContact(@org.jetbrains.annotations.NotNull
    java.lang.String contactId, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String phoneNumber, @org.jetbrains.annotations.Nullable
    java.lang.String email) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.akshar.messaging.data.local.LocalContact> searchContacts(@org.jetbrains.annotations.NotNull
    java.lang.String query) {
        return null;
    }
}