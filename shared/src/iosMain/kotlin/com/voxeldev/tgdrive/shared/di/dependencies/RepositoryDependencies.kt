package com.voxeldev.tgdrive.shared.di.dependencies

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.files.api.file.LocalFilesRepository
import com.voxeldev.tgdrive.main.api.chats.ChatsRepository
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.main.api.profile.ProfileRepository

/**
 * @author nvoxel
 */
data class RepositoryDependencies(
    val authRepository: AuthRepository,
    val chatsRepository: ChatsRepository,
    val filesRepository: FilesRepository,
    val localFilesRepository: LocalFilesRepository,
    val messagesRepository: MessagesRepository,
    val profileRepository: ProfileRepository,
    val supergroupsRepository: SupergroupsRepository,
)
