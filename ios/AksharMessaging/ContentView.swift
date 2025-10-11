import SwiftUI
import FirebaseAnalytics

struct ContentView: View {
    @EnvironmentObject var authManager: AuthenticationManager
    @EnvironmentObject var socketManager: SocketManager
    @State private var isLoading = true
    
    var body: some View {
        Group {
            if isLoading {
                SplashScreenView()
                    .onAppear {
                        checkAuthentication()
                    }
            } else if authManager.isAuthenticated {
                MainTabView()
            } else {
                AuthenticationView()
            }
        }
        .onReceive(NotificationCenter.default.publisher(for: .FCMToken)) { notification in
            if let token = notification.userInfo?["token"] as? String {
                authManager.updateDeviceToken(token)
            }
        }
    }
    
    private func checkAuthentication() {
        Task {
            await authManager.checkAuthentication()
            DispatchQueue.main.async {
                isLoading = false
                // Test Firebase Analytics
                Analytics.logEvent("app_opened", parameters: nil)
                print("📱 Firebase Analytics test completed")
            }
        }
    }
}

// MARK: - Splash Screen
struct SplashScreenView: View {
    @State private var isAnimating = false
    
    var body: some View {
        ZStack {
            // Background gradient
            LinearGradient(
                gradient: Gradient(colors: [
                    Color.blue.opacity(0.8),
                    Color.purple.opacity(0.6)
                ]),
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
            .ignoresSafeArea()
            
            VStack(spacing: 30) {
                // App Logo/Icon
                Image(systemName: "message.fill")
                    .font(.system(size: 80))
                    .foregroundColor(.white)
                    .scaleEffect(isAnimating ? 1.2 : 1.0)
                    .animation(
                        Animation.easeInOut(duration: 1.5)
                            .repeatForever(autoreverses: true),
                        value: isAnimating
                    )
                
                // App Name
                Text("Akshar")
                    .font(.largeTitle)
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                
                // Tagline
                Text("Connect. Chat. Share.")
                    .font(.headline)
                    .foregroundColor(.white.opacity(0.8))
                
                // Loading indicator
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: .white))
                    .scaleEffect(1.2)
            }
        }
        .onAppear {
            isAnimating = true
        }
    }
}

// MARK: - Main Tab View
struct MainTabView: View {
    @EnvironmentObject var authManager: AuthenticationManager
    @EnvironmentObject var socketManager: SocketManager
    @State private var selectedTab = 0
    
    var body: some View {
        TabView(selection: $selectedTab) {
            // Chats Tab
            ChatsView()
                .tabItem {
                    Image(systemName: "message")
                    Text("Chats")
                }
                .tag(0)
            
            // Calls Tab
            CallsView()
                .tabItem {
                    Image(systemName: "phone")
                    Text("Calls")
                }
                .tag(1)
            
            // Status Tab
            StatusView()
                .tabItem {
                    Image(systemName: "camera")
                    Text("Status")
                }
                .tag(2)
            
            // Settings Tab
            SettingsView()
                .tabItem {
                    Image(systemName: "gearshape")
                    Text("Settings")
                }
                .tag(3)
        }
        .accentColor(.blue)
        .onAppear {
            setupTabBarAppearance()
        }
    }
    
    private func setupTabBarAppearance() {
        let appearance = UITabBarAppearance()
        appearance.configureWithOpaqueBackground()
        appearance.backgroundColor = UIColor.systemBackground
        
        UITabBar.appearance().standardAppearance = appearance
        UITabBar.appearance().scrollEdgeAppearance = appearance
    }
}

// MARK: - Authentication View
struct AuthenticationView: View {
    @EnvironmentObject var authManager: AuthenticationManager
    @State private var isLoginMode = true
    @State private var email = ""
    @State private var password = ""
    @State private var username = ""
    @State private var firstName = ""
    @State private var lastName = ""
    @State private var isLoading = false
    @State private var errorMessage = ""
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 20) {
                    // Logo
                    Image(systemName: "message.fill")
                        .font(.system(size: 60))
                        .foregroundColor(.blue)
                        .padding(.top, 40)
                    
                    Text("Akshar")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .padding(.bottom, 20)
                    
                    // Form
                    VStack(spacing: 15) {
                        if !isLoginMode {
                            // Registration fields
                            CustomTextField(
                                title: "Username",
                                text: $username,
                                icon: "person"
                            )
                            
                            CustomTextField(
                                title: "First Name",
                                text: $firstName,
                                icon: "person.fill"
                            )
                            
                            CustomTextField(
                                title: "Last Name",
                                text: $lastName,
                                icon: "person.fill"
                            )
                        }
                        
                        CustomTextField(
                            title: "Email",
                            text: $email,
                            icon: "envelope",
                            keyboardType: .emailAddress
                        )
                        
                        CustomSecureField(
                            title: "Password",
                            text: $password,
                            icon: "lock"
                        )
                        
                        // Error message
                        if !errorMessage.isEmpty {
                            Text(errorMessage)
                                .foregroundColor(.red)
                                .font(.caption)
                        }
                        
                        // Submit button
                        Button(action: handleSubmit) {
                            HStack {
                                if isLoading {
                                    ProgressView()
                                        .progressViewStyle(CircularProgressViewStyle(tint: .white))
                                        .scaleEffect(0.8)
                                }
                                Text(isLoginMode ? "Sign In" : "Sign Up")
                                    .fontWeight(.semibold)
                            }
                            .frame(maxWidth: .infinity)
                            .frame(height: 50)
                            .background(Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                        }
                        .disabled(isLoading || !isFormValid)
                        .opacity(isFormValid ? 1.0 : 0.6)
                    }
                    .padding(.horizontal, 30)
                    
                    // Toggle between login and register
                    Button(action: {
                        isLoginMode.toggle()
                        errorMessage = ""
                    }) {
                        Text(isLoginMode ? "Don't have an account? Sign Up" : "Already have an account? Sign In")
                            .foregroundColor(.blue)
                    }
                    .padding(.top, 10)
                }
                .padding()
            }
            .navigationBarHidden(true)
        }
    }
    
    private var isFormValid: Bool {
        if isLoginMode {
            return !email.isEmpty && !password.isEmpty
        } else {
            return !username.isEmpty && !firstName.isEmpty && !lastName.isEmpty && !email.isEmpty && !password.isEmpty
        }
    }
    
    private func handleSubmit() {
        isLoading = true
        errorMessage = ""
        
        Task {
            do {
                if isLoginMode {
                    try await authManager.signIn(email: email, password: password)
                } else {
                    try await authManager.signUp(
                        username: username,
                        email: email,
                        password: password,
                        firstName: firstName,
                        lastName: lastName
                    )
                }
            } catch {
                DispatchQueue.main.async {
                    errorMessage = error.localizedDescription
                }
            }
            
            DispatchQueue.main.async {
                isLoading = false
            }
        }
    }
}

// MARK: - Custom Text Field
struct CustomTextField: View {
    let title: String
    @Binding var text: String
    let icon: String
    var keyboardType: UIKeyboardType = .default
    
    var body: some View {
        HStack {
            Image(systemName: icon)
                .foregroundColor(.gray)
                .frame(width: 20)
            
            TextField(title, text: $text)
                .keyboardType(keyboardType)
                .textFieldStyle(PlainTextFieldStyle())
        }
        .padding()
        .background(Color.gray.opacity(0.1))
        .cornerRadius(10)
    }
}

// MARK: - Custom Secure Field
struct CustomSecureField: View {
    let title: String
    @Binding var text: String
    let icon: String
    
    var body: some View {
        HStack {
            Image(systemName: icon)
                .foregroundColor(.gray)
                .frame(width: 20)
            
            SecureField(title, text: $text)
                .textFieldStyle(PlainTextFieldStyle())
        }
        .padding()
        .background(Color.gray.opacity(0.1))
        .cornerRadius(10)
    }
}

// MARK: - Placeholder Views (to be implemented)
struct ChatsView: View {
    var body: some View {
        NavigationView {
            Text("Chats")
                .navigationTitle("Chats")
        }
    }
}

struct CallsView: View {
    var body: some View {
        NavigationView {
            Text("Calls")
                .navigationTitle("Calls")
        }
    }
}

struct StatusView: View {
    var body: some View {
        NavigationView {
            Text("Status")
                .navigationTitle("Status")
        }
    }
}

struct SettingsView: View {
    @EnvironmentObject var authManager: AuthenticationManager
    
    var body: some View {
        NavigationView {
            List {
                Section {
                    HStack {
                        AsyncImage(url: URL(string: authManager.currentUser?.avatar ?? "")) { image in
                            image
                                .resizable()
                                .aspectRatio(contentMode: .fill)
                        } placeholder: {
                            Image(systemName: "person.circle.fill")
                                .foregroundColor(.gray)
                        }
                        .frame(width: 50, height: 50)
                        .clipShape(Circle())
                        
                        VStack(alignment: .leading) {
                            Text(authManager.currentUser?.fullName ?? "User")
                                .font(.headline)
                            Text(authManager.currentUser?.email ?? "")
                                .font(.caption)
                                .foregroundColor(.gray)
                        }
                        
                        Spacer()
                    }
                    .padding(.vertical, 8)
                }
                
                Section("Account") {
                    NavigationLink("Profile") {
                        Text("Profile Settings")
                    }
                    NavigationLink("Privacy") {
                        Text("Privacy Settings")
                    }
                    NavigationLink("Notifications") {
                        Text("Notification Settings")
                    }
                }
                
                Section("Support") {
                    NavigationLink("Help") {
                        Text("Help Center")
                    }
                    NavigationLink("About") {
                        Text("About Akshar")
                    }
                }
                
                Section {
                    Button("Sign Out") {
                        authManager.signOut()
                    }
                    .foregroundColor(.red)
                }
            }
            .navigationTitle("Settings")
        }
    }
}

#Preview {
    ContentView()
        .environmentObject(AuthenticationManager())
        .environmentObject(SocketManager())
}
