//
//  RegistrationView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct RegistrationView: View {
    
    private let component: RegistrationComponent
    
    @StateValue
    private var model: RegistrationComponentModel
    
    init(_ component: RegistrationComponent) {
        self.component = component
        _model = StateValue(component.model)
    }
    
    var body: some View {
        if (model.errorText != nil) {
            ErrorView(message: model.errorText!,shouldShowRetry: true, retryCallback: component.onFieldsReset)
        }
        else {
            if (model.isLoading) { LoaderView() }
            else {
                VStack {
                    VStack(spacing: 16) {
                        TextField("Enter your first name", text: Binding(get: { model.firstName }, set: { component.onFirstNameUpdate(firstName: $0) }))
                        
                        TextField("Enter your last name", text: Binding(get: { model.lastName }, set: { component.onLastNameUpdate(lastName: $0) }))
                        
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
