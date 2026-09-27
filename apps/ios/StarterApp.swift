import SwiftUI

@main
struct StarterApp: App {
    @Environment(\.scenePhase) private var scenePhase
    @UIApplicationDelegateAdaptor(AppDelegate.self) private var delegate

    var body: some Scene {
        WindowGroup {
            ComposeView(host: delegate.host)
                .ignoresSafeArea()
                .onOpenURL { delegate.host.openLink(uri: $0.absoluteString) }
                .onChange(of: scenePhase) { phase in
                    if phase == .active { delegate.host.startForegroundWork() }
                    if phase == .background { delegate.enterBackground() }
                }
        }
    }
}
