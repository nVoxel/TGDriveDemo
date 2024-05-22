package com.voxeldev.tgdrive.network.di

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.files.api.file.LocalFilesRepository
import com.voxeldev.tgdrive.main.api.chats.ChatsRepository
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.main.api.profile.ProfileRepository
import com.voxeldev.tgdrive.network.repository.DesktopAuthRepository
import com.voxeldev.tgdrive.network.repository.DesktopChatsRepository
import com.voxeldev.tgdrive.network.repository.DesktopFilesRepository
import com.voxeldev.tgdrive.network.repository.DesktopLocalFilesRepository
import com.voxeldev.tgdrive.network.repository.DesktopMessagesRepository
import com.voxeldev.tgdrive.network.repository.DesktopProfileRepository
import com.voxeldev.tgdrive.network.repository.DesktopSupergroupsRepository
import kotlinx.telegram.core.CommonTelegramFlow
import kotlinx.telegram.core.TelegramFlow
import org.koin.dsl.module
import java.io.File

/**
 * @author nvoxel
 */
val desktopNetworkModule = module {
    includes(desktopMappersModule)

    val telegramFlow = TelegramFlow().also { it.attachClient() }

    single<TelegramFlow> { telegramFlow }
    single<CommonTelegramFlow> { telegramFlow }

    single<AuthRepository> {
        DesktopAuthRepository(
            telegramFlow = get(),
        )
    }

    single<ChatsRepository> {
        DesktopChatsRepository(
            telegramFlow = get(),
        )
    }

    single<FilesRepository> {
        DesktopFilesRepository(
            telegramFlow = get(),
            messagesRepository = get(),
            fileMapper = get(),
            messageFileMapper = get(),
        )
    }

    single<LocalFilesRepository> {
        DesktopLocalFilesRepository(
            cacheDirectoryPath = System.getProperty("user.dir") + File.separator + "cache",
        )
    }

    single<MessagesRepository> {
        DesktopMessagesRepository(
            telegramFlow = get(),
            messagesMapper = get(),
            chatsMapper = get(),
        )
    }

    single<ProfileRepository> {
        DesktopProfileRepository(
            telegramFlow = get(),
            userMapper = get(),
        )
    }

    single<SupergroupsRepository> {
        DesktopSupergroupsRepository(
            telegramFlow = get(),
            chatsMapper = get(),
            supergroupMapper = get(),
        )
    }
}
