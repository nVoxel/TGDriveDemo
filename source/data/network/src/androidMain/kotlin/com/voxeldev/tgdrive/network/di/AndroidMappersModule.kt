package com.voxeldev.tgdrive.network.di

import com.voxeldev.tgdrive.mappers.auth.TdLibParametersMapper
import com.voxeldev.tgdrive.mappers.auth.UpdateAuthorizationStateMapper
import com.voxeldev.tgdrive.mappers.channels.SupergroupMapper
import com.voxeldev.tgdrive.mappers.chats.ChatsMapper
import com.voxeldev.tgdrive.mappers.files.MessageFileMapper
import com.voxeldev.tgdrive.mappers.media.AudioMapper
import com.voxeldev.tgdrive.mappers.media.DocumentMapper
import com.voxeldev.tgdrive.mappers.media.FileMapper
import com.voxeldev.tgdrive.mappers.media.PhotoMapper
import com.voxeldev.tgdrive.mappers.media.ThumbnailMapper
import com.voxeldev.tgdrive.mappers.media.VideoMapper
import com.voxeldev.tgdrive.mappers.messages.MessagesMapper
import com.voxeldev.tgdrive.mappers.user.UserMapper
import org.koin.dsl.module

/**
 * @author nvoxel
 */
internal val androidMappersModule = module {

    single { TdLibParametersMapper() }
    single { UpdateAuthorizationStateMapper() }

    single { FileMapper() }
    single { ThumbnailMapper(fileMapper = get()) }
    single { DocumentMapper(thumbnailMapper = get(), fileMapper = get()) }
    single { PhotoMapper(fileMapper = get()) }
    single { VideoMapper(thumbnailMapper = get(), fileMapper = get()) }
    single { AudioMapper(fileMapper = get()) }

    single { ChatsMapper(fileMapper = get()) }

    single {
        MessagesMapper(
            documentMapper = get(),
            photoMapper = get(),
            videoMapper = get(),
            audioMapper = get()
        )
    }

    single { UserMapper(fileMapper = get()) }

    single { SupergroupMapper(photoMapper = get()) }

    single { MessageFileMapper() }
}
