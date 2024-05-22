//
//  MainView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct MainView: View {
    
    private let component: MainComponent
    
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, MainComponentChild>>
    
    init (_ component: MainComponent) {
        self.component = component
        self.childStack = ObservableValue(component.childStack)
    }
    
    var body: some View {
        VStack {
            ChildView(child: self.childStack.value.active.instance)
                .frame(maxHeight: .infinity)
            
            HStack(alignment: .bottom, spacing: 32) {
                Button(action: component.onFavoritesTabClicked) {
                    Label("Favorites", systemImage: "star")
                        .labelStyle(VerticalLabelStyle())
                        .opacity(self.childStack.value.active.instance is MainComponentChildFavoritesChild ? 1 : 0.5)
                }
                
                Button(action: component.onFilesTabClicked) {
                    Label("Files", systemImage: "folder")
                        .labelStyle(VerticalLabelStyle())
                        .opacity(self.childStack.value.active.instance is MainComponentChildFilesChild ? 1 : 0.5)
                }
                
                Button(action: component.onSettingsTabClicked) {
                    Label("Settings", systemImage: "gear")
                        .labelStyle(VerticalLabelStyle())
                        .opacity(self.childStack.value.active.instance is MainComponentChildSettingsChild ? 1 : 0.5)
                }
            }
        }
    }
}

private struct ChildView: View {
    let child: MainComponentChild
    
    var body: some View {
        switch child {
        case let child as MainComponentChildFavoritesChild: FavoritesView(component: child.component)
        case let child as MainComponentChildFilesChild: FilesView(component: child.component)
        case let child as MainComponentChildSettingsChild: SettingsView(component: child.component)
        default: EmptyView()
        }
    }
}

private struct VerticalLabelStyle: LabelStyle {
    func makeBody(configuration: Configuration) -> some View {
        VStack(alignment: .center, spacing: 8) {
            configuration.icon
            configuration.title
        }
    }
}
