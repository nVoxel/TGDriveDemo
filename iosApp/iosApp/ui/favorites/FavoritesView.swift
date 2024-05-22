//
//  FavoritesView.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct FavoritesView: View {
    
    private let component: FavoritesComponent
    
    @StateValue
    private var model: FavoritesComponentModel
    
    @StateObject var documentPreview = DocumentPreview()
    
    init(component: FavoritesComponent) {
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
                    List(model.favoriteFiles, id: \.messageFile.file.id) { favorite in
                        Button(action: { documentPreview.presentDocument(url: URL(string: "file://\(favorite.messageFile.file.local.path)")!) }) {
                            HStack(alignment: .center) {
                                VStack(alignment: .leading) {
                                    Text(favorite.messageFile.fileName)
                                    Text("Size in bytes: \(favorite.messageFile.file.expectedSize)")
                                }
                                
                                Spacer()
                                
                                if (favorite.favoriteMessage != nil) {
                                    Button(action: { component.onRemoveFavoriteClicked(favoriteMessage: favorite.favoriteMessage!) }) {
                                        Image(systemName: "star.fill")
                                    }
                                }
                            }
                        }
                    }
                    .refreshable {
                        component.onReloadClicked()
                    }
                } toolbar: { }
            }
        }
    }
}
