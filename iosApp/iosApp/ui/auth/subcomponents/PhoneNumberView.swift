//
//  PhoneNumberView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct PhoneNumberView: View {
    
    private let component: PhoneNumberComponent
    
    @StateValue
    private var model: PhoneNumberComponentModel
    
    init(_ component: PhoneNumberComponent) {
        self.component = component
        _model = StateValue(component.model)
    }
    
    var body: some View {
        if (model.errorText != nil) {
            ErrorView(message: model.errorText!,shouldShowRetry: true, retryCallback: component.onPhoneReset)
        }
        else {
            if (model.isLoading) { LoaderView() }
            else {
                VStack {
                    VStack(spacing: 16) {
                        TextField("Enter phone", text: Binding(get: { model.phoneNumber }, set: { component.onPhoneUpdate(phone: $0) }))
                        
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
