//
//  AuthView.swift
//  iosApp
//
//  Created by nVoxel on 23.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import TGDriveKit

struct AuthView: View {
    
    private let component: AuthComponent
    
    @ObservedObject
    private var childStack: ObservableValue<ChildStack<AnyObject, AuthComponentChild>>
    
    init (_ component: AuthComponent) {
        self.component = component
        self.childStack = ObservableValue(component.childStack)
    }
    
    var body: some View {
        let child = self.childStack.value.active.instance
        
        switch child {
        case let code as AuthComponentChildCodeChild:
            CodeView(code.component)
        case let confirmation as AuthComponentChildConfirmationChild:
            ConfirmationView(confirmation.component)
        case let password as AuthComponentChildPasswordChild:
            PasswordView(password.component)
        case let phoneNumber as AuthComponentChildPhoneNumberChild:
            PhoneNumberView(phoneNumber.component)
        case let registration as AuthComponentChildRegistrationChild:
            RegistrationView(registration.component)
        case let welcome as AuthComponentChildWelcomeChild:
            WelcomeView(welcome.component)
        default: EmptyView()
        }
    }
}
