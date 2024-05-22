//
//  DocumentMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class DocumentMapper {
    
    private let thumbnailMapper: ThumbnailMapper
    private let fileMapper: FileMapper
    
    init(thumbnailMapper: ThumbnailMapper, fileMapper: FileMapper) {
        self.thumbnailMapper = thumbnailMapper
        self.fileMapper = fileMapper
    }
    
    func toResponse(document: Document) -> CommonDocument {
        return CommonDocument(
            fileName: document.fileName,
            mimeType: document.mimeType,
            thumbnail: document.thumbnail != nil ? thumbnailMapper.toResponse(thumbnail: document.thumbnail!) : nil,
            file: fileMapper.toResponse(file: document.document)
        )
    }
}
