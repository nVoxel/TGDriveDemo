//
//  ConfirmationView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct ConfirmationView: View {
    
    private let component: ConfirmationComponent
    
    init(_ component: ConfirmationComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            VStack(spacing: 16) {
                Text(component.model.confirmationLink)
            }
            .transition(.move(edge: .top).combined(with: .opacity))
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
    }
}
