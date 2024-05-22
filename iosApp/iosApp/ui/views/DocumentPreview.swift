//
//  DocumentController.swift
//  iosApp
//
//  Created by nVoxel on 14.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit

class DocumentPreview: NSObject, ObservableObject, UIDocumentInteractionControllerDelegate {
    let controller = UIDocumentInteractionController()
    func presentDocument(url: URL) {
        controller.delegate = self
        controller.url = url
        controller.presentPreview(animated: true)
    }

    func documentInteractionControllerViewControllerForPreview(_: UIDocumentInteractionController) -> UIViewController {
        return (UIApplication.shared
        .connectedScenes.lazy
        .compactMap { $0.activationState == .foregroundActive ? ($0 as? UIWindowScene) : nil }
        .first(where: { $0.keyWindow != nil })?
        .keyWindow?
        .rootViewController)!
    }
}
