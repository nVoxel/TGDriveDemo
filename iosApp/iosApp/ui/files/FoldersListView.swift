//
//  FoldersListView.swift
//  iosApp
//
//  Created by nVoxel on 13.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import SwiftUI
import TGDriveKit

struct FoldersListView : View {
    
    private let component: FoldersListComponent
    
    @StateValue
    private var model: FoldersListComponentModel
    
    @State
    private var shareSheetVisible: Bool = false
    
    @State
    private var shareLink: String? = nil
    
    init(component: FoldersListComponent) {
        self.component = component
        _model = StateValue(component.model)
    }
    
    var body: some View {
        if (model.errorText != nil) {
            ErrorView(message: model.errorText!, shouldShowRetry: true, retryCallback: component.onReloadClicked)
        }
        else {
            if (model.isLoading) { LoaderView() }
            else {
                NavigationWrapper {
                    List(model.folders, id: \.chatId) { folder in
                        Button(
                            action: { component.onFolderClicked(id: folder.chatId, name: folder.title) }
                        ) {
                            HStack(alignment: .center) {
                                VStack(alignment: .leading) {
                                    Text(folder.title)
                                    
                                    Text("\(folder.fileCount) files")
                                }
                                
                                Spacer()
                                
                                Button(action: {
                                    component.onOpenFolderMenuClicked(chatId: folder.chatId)
                                    shareLink = folder.inviteLink?.link
                                }) {
                                    Text("Edit")
                                }
                                .confirmationDialog(
                                    "Menu",
                                    isPresented: Binding(get: { model.folderMenuVisible }, set: { _ in component.onDismissFolderMenuClicked() })
                                ) {
                                    Button(action: component.onRenameFolderClicked) {
                                        Label("Rename", systemImage: "pencil")
                                    }
                                    
                                    if (folder.inviteLink != nil) {
                                        Button(action: { shareSheetVisible = true }) {
                                            Label("Share", systemImage: "paperplane.fill")
                                        }
                                    }
                                    
                                    Button(action: component.onDeleteFolderClicked) {
                                        Label("Delete", systemImage: "trash.fill")
                                    }
                                }
                                .confirmationDialog(
                                    "Delete folder",
                                    isPresented: Binding(get: { model.deleteFolderDialogVisible }, set: { _ in component.onDismissDeleteFolderClicked() })
                                ) {
                                    Button(role: .destructive, action: component.onConfirmDeleteFolderClicked) {
                                        Label("Delete", systemImage: "trash.fill")
                                    }
                                }
                                .alert(
                                    "Set new folder name",
                                    isPresented: Binding(get: { model.renameFolderDialogVisible }, set: {_ in})
                                ) {
                                    TextField("New folder name", text: Binding(get: {model.pendingFolderTitle}, set: { component.onFolderTitleUpdate(title: $0) }))
                                    
                                    Button("Rename folder", action: component.onConfirmRenameFolderClicked)
                                    
                                    Button("Cancel", action: component.onDismissRenameFolderClicked)
                                }
                            }
                        }
                    }
                    .sheet(isPresented: $shareSheetVisible, onDismiss: { shareSheetVisible = false }) {
                        ActivityView(text: shareLink!)
                    }
                    .refreshable {
                        component.onReloadClicked()
                    }
                } toolbar: {
                    Button (action: component.onCreateFolderClicked) {
                        Label("New folder", systemImage: "plus")
                    }
                    .alert("Set folder name", isPresented: Binding(get: { model.createFolderDialogVisible }, set: { _ in })) {
                        TextField("Folder name", text: Binding(get: {model.pendingFolderTitle}, set: {component.onFolderTitleUpdate(title: $0)}))
                        
                        Button("Create folder", action: component.onConfirmCreateFolderClicked)
                        
                        Button("Cancel", action: component.onDismissCreateFolderClicked)
                    }
                }
            }
        }
    }
}
