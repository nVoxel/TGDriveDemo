//
//  ThumbnailMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class ThumbnailMapper {
    
    private let fileMapper: FileMapper
    
    init(fileMapper: FileMapper) {
        self.fileMapper = fileMapper
    }
    
    func toResponse(thumbnail: Thumbnail) -> CommonThumbnail? {
        return CommonThumbnail(
            width: Int32(thumbnail.width),
            height: Int32(thumbnail.height),
            file: fileMapper.toResponse(file: thumbnail.file)
        )
    }
}
