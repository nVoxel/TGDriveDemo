//
//  WelcomeView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct WelcomeView: View {
    
    private let component: WelcomeComponent
    
    init(_ component: WelcomeComponent) {
        self.component = component
    }
    
    var body: some View {
        VStack {
            VStack(spacing: 16) {
                Text("TGDrive")
            }
            .transition(.move(edge: .top).combined(with: .opacity))
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .padding()
    }
}
