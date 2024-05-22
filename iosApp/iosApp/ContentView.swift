import SwiftUI
import TGDriveKit

struct ContentView: View {
    
    private var test: URL? = URL(fileURLWithPath: "")
    
    @State
    private var componentHolder =
    ComponentHolder {
        DefaultRootComponent(
            componentContext: $0,
            storeFactory: DefaultStoreFactory()
        )
    }
    
    var body: some View {
        RootView(componentHolder.component)
            .onAppear { LifecycleRegistryExtKt.resume(self.componentHolder.lifecycle) }
            .onDisappear { LifecycleRegistryExtKt.stop(self.componentHolder.lifecycle) }
    }
}
