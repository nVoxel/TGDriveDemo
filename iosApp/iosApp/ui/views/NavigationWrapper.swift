//
//  NavigationWrapper.swift
//  iosApp
//
//  Created by nVoxel on 03.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct NavigationWrapper<Content, Toolbar>: View
where Content: View, Toolbar: View
{
    
    private var content: Content
    private var toolbar: Toolbar
    
    init(
        @ViewBuilder content: () -> Content,
        @ViewBuilder toolbar: () -> Toolbar
    ) {
        self.content = content()
        self.toolbar = toolbar()
    }
    
    var body: some View {
        if #available(iOS 16.0, *) {
            NavigationStack {
                VStack {
                    content
                }
                .toolbar {
                    toolbar
                }
            }
        } else {
            NavigationView {
                VStack {
                    content
                }
                .toolbar {
                    toolbar
                }
            }
        }
    }
}
