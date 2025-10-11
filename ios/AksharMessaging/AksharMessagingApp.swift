import SwiftUI
import FirebaseCore
import FirebaseMessaging

@main
struct AksharMessagingApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    @StateObject private var authManager = AuthenticationManager()
    @StateObject private var socketManager = SocketManager()
    
    init() {
        // Initialize Firebase
        FirebaseApp.configure()
        print("🔥 Firebase initialized successfully!")
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(authManager)
                .environmentObject(socketManager)
        }
    }
}

// MARK: - App Delegate
class AppDelegate: NSObject, UIApplicationDelegate, MessagingDelegate, UNUserNotificationCenterDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // Configure push notifications
        configurePushNotifications(application)
        
        // Set messaging delegate
        Messaging.messaging().delegate = self
        
        return true
    }
    
    // MARK: - Push Notifications Configuration
    private func configurePushNotifications(_ application: UIApplication) {
        UNUserNotificationCenter.current().delegate = self
        
        let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
        UNUserNotificationCenter.current().requestAuthorization(
            options: authOptions,
            completionHandler: { granted, error in
                if granted {
                    DispatchQueue.main.async {
                        application.registerForRemoteNotifications()
                    }
                }
            }
        )
    }
    
    // MARK: - APNs Token Registration
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        Messaging.messaging().apnsToken = deviceToken
    }
    
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("Failed to register for remote notifications: \(error)")
    }
    
    // MARK: - Firebase Messaging Delegate
    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("Firebase registration token: \(String(describing: fcmToken))")
        
        let dataDict: [String: String] = ["token": fcmToken ?? ""]
        NotificationCenter.default.post(
            name: Notification.Name("FCMToken"),
            object: nil,
            userInfo: dataDict
        )
    }
    
    // MARK: - UNUserNotificationCenterDelegate
    func userNotificationCenter(_ center: UNUserNotificationCenter, willPresent notification: UNNotification, withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        let userInfo = notification.request.content.userInfo
        
        // Handle foreground notifications
        if let chatId = userInfo["chatId"] as? String {
            NotificationCenter.default.post(
                name: NSNotification.Name("NewMessageReceived"),
                object: nil,
                userInfo: ["chatId": chatId]
            )
        }
        
        // Show notification even when app is in foreground
        completionHandler([.banner, .sound, .badge])
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter, didReceive response: UNNotificationResponse, withCompletionHandler completionHandler: @escaping () -> Void) {
        let userInfo = response.notification.request.content.userInfo
        
        // Handle notification tap
        if let chatId = userInfo["chatId"] as? String {
            NotificationCenter.default.post(
                name: NSNotification.Name("NavigateToChat"),
                object: nil,
                userInfo: ["chatId": chatId]
            )
        }
        
        completionHandler()
    }
}

// MARK: - Notification Names
extension NSNotification.Name {
    static let FCMToken = NSNotification.Name("FCMToken")
    static let NewMessageReceived = NSNotification.Name("NewMessageReceived")
    static let NavigateToChat = NSNotification.Name("NavigateToChat")
}
