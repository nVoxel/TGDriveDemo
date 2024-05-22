//
//  AudioMapper.swift
//  iosApp
//
//  Created by nVoxel on 04.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class AudioMapper {
    
    private let fileMapper: FileMapper
    
    init(fileMapper: FileMapper) {
        self.fileMapper = fileMapper
    }
    
    func toResponse(audio: Audio) -> CommonAudio {
        return CommonAudio(
            duration: Int32(audio.duration),
            title: audio.title,
            performer: audio.performer,
            fileName: audio.fileName,
            mimeType: audio.mimeType,
            file: fileMapper.toResponse(file: audio.audio)
        )
    }
}
