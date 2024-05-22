//
//  FolderView.swift
//  iosApp
//
//  Created by nVoxel on 13.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct FolderView: View {
    
    private let component: FolderComponent
    
    @StateValue
    private var model: FolderComponentModel
    
    @StateObject var documentPreview = DocumentPreview()
    
    init(component: FolderComponent) {
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
                NavigationView { // todo: pass toolbar items to NavigationWrapper
                    List(model.files, id: \.messageFile.file.id) { file in
                        Button(
                            action: {
                                if (model.downloadingFiles[KotlinInt(int: file.messageFile.file.id)] == nil) {
                                    if (file.messageFile.file.isDownloaded()) {
                                        documentPreview.presentDocument(url: URL(string: "file://\(file.messageFile.file.local.path)")!)
                                    } else {
                                        component.onFileClicked(id: file.messageFile.file.id)
                                    }
                                }
                            }
                        ){
                            HStack(alignment: .center) {
                                VStack(alignment: .leading) {
                                    Text(file.messageFile.fileName)
                                    Text("Size in bytes: \(file.messageFile.file.expectedSize)")
                                    Text("Downloaded: \(file.messageFile.file.local.isDownloadingCompleted ? "Yes" : "No")")
                                    if let progress = model.downloadingFiles[KotlinInt(int: file.messageFile.file.id)] {
                                        ProgressView(value: Float(truncating: progress))
                                    }
                                    if (file.messageFile.caption != nil && !file.messageFile.caption!.isEmpty) {
                                        Text(file.messageFile.caption!)
                                    }
                                }
                                
                                Spacer()
                                
                                if (file.messageFile.file.isDownloaded()) {
                                    Button(action: {component.onFileFavoriteClicked(messageId: file.messageFile.messageId)}) {
                                        Image(systemName: file.favoriteMessage == nil ? "star" : "star.fill")
                                    }
                                    
                                    Spacer().frame(width: 20)
                                }
                                
                                Button(action: { component.onOpenFileMenuClicked(messageId: file.messageFile.messageId) }) {
                                    Image(systemName: "ellipsis")
                                }
                                .confirmationDialog(
                                    "Menu",
                                    isPresented: Binding(get: { model.fileMenuVisible }, set: { _ in component.onDismissFileMenuClicked() })
                                ) {
                                    Button(action: component.onDeleteFileClicked) {
                                        Label("Delete", systemImage: "trash.fill")
                                    }
                                }
                                .confirmationDialog(
                                    "Delete file",
                                    isPresented: Binding(get: { model.deleteFileDialogVisible }, set: { _ in component.onDismissDeleteFileClicked() })
                                ) {
                                    Button(role: .destructive, action: component.onConfirmDeleteFileClicked) {
                                        Label("Delete", systemImage: "trash.fill")
                                    }
                                }
                            }
                            .padding(8)
                        }
                    }
                    .refreshable {
                        component.onReloadClicked()
                    }
                    .toolbar {
                        ToolbarItem(placement: .topBarLeading) {
                            Button(action: component.onCloseClicked) {
                                Label("Back", systemImage: "chevron.left")
                            }
                        }
                        
                        ToolbarItem(placement: .topBarLeading) {
                            Text(model.folderName)
                        }
                        
                        ToolbarItem(placement: .topBarTrailing) {
                            Button(action: component.onUploadsBottomSheetToggle) {
                                Label("Uploads", systemImage: "arrow.up.doc")
                            }.sheet(
                                isPresented: Binding(
                                    get: { model.uploadsBottomSheetActive },
                                    set: { val in }
                                ),
                                onDismiss: { component.onUploadsBottomSheetToggle() }
                            ) {
                                VStack {
                                    FilePicker(types: [.mp3], allowMultiple: false, title: "Select a file") { handler in
                                        print(handler[0].absoluteString)
                                        self.component.onUploadFileSelected(filePointer: handler[0].absoluteString)
                                    }
                                    
                                    List(model.uploadingFiles, id: \.id) { localFile in
                                        VStack {
                                            Text(localFile.name)
                                            ProgressView(value: Float(truncating: localFile.uploadProgress ?? 0))
                                        }
                                        .padding(8)
                                    }
                                    .padding(8)
                                }
                                .padding(16)
                            }
                        }
                    }
                }
            }
        }
    }
}
