//
//  VideoMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class VideoMapper {
    
    private let fileMapper: FileMapper
    private let thumbnailMapper: ThumbnailMapper
    
    init(fileMapper: FileMapper, thumbnailMapper: ThumbnailMapper) {
        self.fileMapper = fileMapper
        self.thumbnailMapper = thumbnailMapper
    }
    
    func toResponse(video: Video) -> CommonVideo {
        return CommonVideo(
            duration: Int32(video.duration),
            width: Int32(video.width),
            height: Int32(video.height),
            fileName: video.fileName,
            mimeType: video.mimeType,
            supportsStreaming: video.supportsStreaming,
            thumbnail: video.thumbnail != nil ? thumbnailMapper.toResponse(thumbnail: video.thumbnail!) : nil,
            video: fileMapper.toResponse(file: video.video)
        )
    }
}
