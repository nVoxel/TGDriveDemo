//
//  FilesView.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct FilesView: View {
    
    private let component: FilesComponent
    
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, FilesComponentChild>>
    
    init(component: FilesComponent) {
        self.component = component
        self.childStack = ObservableValue(component.childStack)
    }
    
    var body: some View {
        ChildView(child: self.childStack.value.active.instance)
    }
}

private struct ChildView : View {
    let child: FilesComponentChild
    
    var body: some View {
        switch child {
        case let child as FilesComponentChildListChild:
            FoldersListView(component: child.component)
        case let child as FilesComponentChildFolderChild:
            FolderView(component: child.component)
        default: EmptyView()
        }
    }
}
