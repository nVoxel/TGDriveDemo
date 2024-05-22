//
//  PhotoMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class PhotoMapper {
    
    private let fileMapper: FileMapper
    
    init(fileMapper: FileMapper) {
        self.fileMapper = fileMapper
    }
    
    func toResponse(photo: Photo) -> CommonPhoto {
        return CommonPhoto(
            sizes: toResponse(sizes: photo.sizes)
        )
    }
    
    func toResponse(sizes: [PhotoSize]) -> [CommonPhotoSize] {
        return sizes.map { toResponse(size: $0) }
    }
    
    private func toResponse(size: PhotoSize) -> CommonPhotoSize {
        return CommonPhotoSize(
            type: size.type,
            photo: fileMapper.toResponse(file: size.photo),
            width: Int32(size.width),
            height: Int32(size.height)
        )
    }
}
