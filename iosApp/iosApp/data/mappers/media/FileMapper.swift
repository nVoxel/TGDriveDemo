//
//  FileMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class FileMapper {
    
    func toResponse(file: File) -> CommonFile {
        return CommonFile(
            id: Int32(file.id),
            size: file.size,
            expectedSize: file.expectedSize,
            local: toResponse(localFile: file.local),
            remote: toResponse(remoteFile: file.remote)
        )
    }
    
    private func toResponse(localFile: LocalFile) -> CommonFileLocal {
        return CommonFileLocal(
            path: localFile.path,
            canBeDownloaded: localFile.canBeDownloaded,
            canBeDeleted: localFile.canBeDeleted,
            isDownloadingActive: localFile.isDownloadingActive,
            isDownloadingCompleted: localFile.isDownloadingCompleted,
            downloadOffset: localFile.downloadOffset,
            downloadedPrefixSize: localFile.downloadedPrefixSize,
            downloadedSize: localFile.downloadedSize
        )
    }
    
    private func toResponse(remoteFile: RemoteFile) -> CommonFileRemote {
        return CommonFileRemote(
            id: remoteFile.id,
            uniqueId: remoteFile.uniqueId,
            isUploadingActive: remoteFile.isUploadingActive,
            isUploadingCompleted: remoteFile.isUploadingCompleted,
            uploadedSize: remoteFile.uploadedSize
        )
    }
}
