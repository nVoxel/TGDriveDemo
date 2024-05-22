import SwiftUI
import TGDriveKit
import TDLibKit

@main
struct iOSApp: App {
    
    private let clientWrapper = ClientWrapper()
    
    init() {
        clientWrapper.startClient(shouldRestart: false)
        
        KoinHelperKt.doInitKoin(
            commonDependencies: clientWrapper.commonDependencies,
            repositoryDependencies: clientWrapper.repositoryDependencies
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
