//
//  CodeView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct CodeView: View {
    
    private let component: CodeComponent
    
    @StateValue
    private var model: CodeComponentModel
    
    init(_ component: CodeComponent) {
        self.component = component
        _model = StateValue(component.model)
    }
    
    var body: some View {
        if (model.errorText != nil) {
            ErrorView(message: model.errorText!,shouldShowRetry: true, retryCallback: component.onCodeReset)
        }
        else {
            if (model.isLoading) { LoaderView() }
            else {
                VStack {
                    VStack(spacing: 16) {
                        TextField("Enter auth code", text: Binding(get: { model.code }, set: { component.onCodeUpdate(code: $0) }))
                        
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
