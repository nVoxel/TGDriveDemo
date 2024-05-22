//
//  ErrorView.swift
//  iosApp
//
//  Created by nVoxel on 26.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct ErrorView : View {
    
    private let message: String
    private let shouldShowRetry: Bool
    private let retryCallback: () -> Void
    
    init(message: String, shouldShowRetry: Bool) {
        self.message = message
        self.shouldShowRetry = shouldShowRetry
        self.retryCallback = {}
    }
    
    init(message: String, shouldShowRetry: Bool, retryCallback: @escaping () -> Void) {
        self.message = message
        self.shouldShowRetry = shouldShowRetry
        self.retryCallback = retryCallback
    }
    
    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            Text("Error: \(message)")
                .multilineTextAlignment(.center)
            if shouldShowRetry {
                Button(action: { retryCallback() }) {
                    Image(systemName: "arrow.counterclockwise")
                        .frame(width: 20, height: 20)
                    Text("Retry")
                        .padding(.horizontal, 4)
                }
                .padding(.top, 16)
            }
        }
    }
}
