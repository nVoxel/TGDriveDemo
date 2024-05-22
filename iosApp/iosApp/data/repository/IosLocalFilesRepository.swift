//
//  LocalFilesRepository.swift
//  iosApp
//
//  Created by nVoxel on 04.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TGDriveKit

class IosLocalFilesRepository : LocalFilesRepository {
    
    private let documentsDirectory: URL
    
    init(documentsDirectory: URL) {
        self.documentsDirectory = documentsDirectory
    }
    
    func getLocalFilePath(filePointer: String) async throws -> CommonLocalFile {
        let fileUrl = URL(string: filePointer)
        let fileName = fileUrl?.lastPathComponent
        
        let destinationUrl = documentsDirectory.appendingPathComponent(fileName!, conformingTo: .fileURL)
        
        if ((try? !destinationUrl.checkResourceIsReachable()) ?? true) {
            try FileManager.default.copyItem(at: fileUrl!, to: destinationUrl)
        }
        
        return CommonLocalFile(id: nil, name: fileName!, path: destinationUrl.absoluteString.replacingOccurrences(of: "file://", with: ""), uploadProgress: nil)
    }
    
    func getSplitFilePaths(filePointer: String) async throws -> [CommonLocalFile] {
        return []
    }
    
}
