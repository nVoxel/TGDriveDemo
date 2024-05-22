package com.voxeldev.tgdrive.shared.di

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.database.di.databaseModule
import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.files.api.file.LocalFilesRepository
import com.voxeldev.tgdrive.main.api.chats.ChatsRepository
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.main.api.profile.ProfileRepository
import com.voxeldev.tgdrive.shared.di.dependencies.CommonDependencies
import com.voxeldev.tgdrive.shared.di.dependencies.RepositoryDependencies
import kotlinx.telegram.core.CommonTelegramFlow
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * @author nvoxel
 */
fun initKoin(
    commonDependencies: CommonDependencies,
    repositoryDependencies: RepositoryDependencies,
) {
    val commonNetworkModule = module {
        single<CommonTelegramFlow> { commonDependencies.commonTelegramFlow }

        single<AuthRepository> { repositoryDependencies.authRepository }
        single<ChatsRepository> { repositoryDependencies.chatsRepository }
        single<FilesRepository>{ repositoryDependencies.filesRepository }
        single<LocalFilesRepository> { repositoryDependencies.localFilesRepository }
        single<MessagesRepository> { repositoryDependencies.messagesRepository }
        single<ProfileRepository> { repositoryDependencies.profileRepository }
        single<SupergroupsRepository> { repositoryDependencies.supergroupsRepository }
    }

    startKoin {
        modules(commonNetworkModule, databaseModule)
    }
}
