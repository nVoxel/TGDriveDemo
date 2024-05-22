//
//  RootView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct RootView: View {
    
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, RootComponentChild>>
    
    init(_ component: RootComponent) {
        self.childStack = ObservableValue(component.childStack)
    }
    
    var body: some View {
        let child = self.childStack.value.active.instance
        
        switch child {
        case let child as RootComponentChildAuthChild:
            AuthView(child.component)
        case let child as RootComponentChildMainChild:
            MainView(child.component)
        default: EmptyView()
        }
    }
}
