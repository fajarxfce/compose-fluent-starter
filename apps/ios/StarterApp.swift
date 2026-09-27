import SwiftUI

@main
struct StarterApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var delegate

    var body: some Scene {
        WindowGroup {
            ComposeView(host: delegate.host)
                .ignoresSafeArea()
                .onOpenURL { delegate.host.openLink(uri: $0.absoluteString) }
        }
    }
}
