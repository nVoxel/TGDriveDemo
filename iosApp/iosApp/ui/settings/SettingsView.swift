//
//  SettingsView.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct SettingsView: View {
    
    private let component: SettingsComponent
    
    @StateValue
    private var model: SettingsComponentModel
    
    init(component: SettingsComponent) {
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
                    Form {
                        Section(
                            content: {
                                Button(action: {}) {
                                    HStack {
                                        Image(systemName: "arrow.2.circlepath")
                                        Text("Synchronization")
                                        Spacer()
                                        Image(systemName: "chevron.right")
                                    }
                                }
                                .foregroundColor(.primary)
                            },
                            header: {
                                Text("TASKS")
                            }
                        )
                        
                        Section(
                            content: {
                                Button(action: { component.onLogOutClicked() }) {
                                    HStack {
                                        Image(systemName: "rectangle.portrait.and.arrow.right")
                                        Text("Log Out")
                                        Spacer()
                                        Image(systemName: "chevron.right")
                                    }
                                    .foregroundStyle(.red)
                                }
                                .foregroundColor(.primary)
                                .confirmationDialog(
                                    "Are you sure you want to log out?",
                                    isPresented: Binding(get: { model.logOutDialogVisible }, set: { _ in component.onDismissLogOutClicked() })
                                ) {
                                    Button(role: .destructive, action: component.onConfirmLogOutClicked) {
                                        Label("Log Out", systemImage: "rectangle.portrait.and.arrow.right")
                                    }
                                }
                            },
                            header: {
                                Text("PROFILE")
                            }
                        )
                    }
                    .refreshable {
                        component.onReloadClicked()
                    }
                } toolbar: { }
            }
        }
    }
}
