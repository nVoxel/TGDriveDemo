//
//  PasswordView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct PasswordView: View {
    
    private let component: PasswordComponent
    
    @StateValue
    private var model: PasswordComponentModel
    
    init(_ component: PasswordComponent) {
        self.component = component
        _model = StateValue(component.model)
    }
    
    var body: some View {
        if (model.errorText != nil) {
            ErrorView(message: model.errorText!, shouldShowRetry: true, retryCallback: component.onPasswordReset)
        }
        else {
            if (model.isLoading) { LoaderView() }
            else {
                VStack {
                    VStack(spacing: 16) {
                        SecureField("Enter password", text: Binding(get: { model.password }, set: { component.onPasswordUpdate(password: $0) }))
                        
                        Button(
                            action: { component.onContinueClicked() },
                            label: { Text("Continue") }
                        )
                    }
                    .transition(.move(edge: .top).combined(with: .opacity))
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
                .padding(16)
            }
        }
    }
}
