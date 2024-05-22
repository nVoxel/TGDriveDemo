package com.voxeldev.tgdrive.network.di

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.files.api.file.LocalFilesRepository
import com.voxeldev.tgdrive.main.api.chats.ChatsRepository
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.main.api.profile.ProfileRepository
import com.voxeldev.tgdrive.network.repository.AndroidAuthRepository
import com.voxeldev.tgdrive.network.repository.AndroidChatsRepository
import com.voxeldev.tgdrive.network.repository.AndroidFilesRepository
import com.voxeldev.tgdrive.network.repository.AndroidLocalFilesRepository
import com.voxeldev.tgdrive.network.repository.AndroidMessagesRepository
import com.voxeldev.tgdrive.network.repository.AndroidProfileRepository
import com.voxeldev.tgdrive.network.repository.AndroidSupergroupsRepository
import kotlinx.telegram.core.CommonTelegramFlow
import kotlinx.telegram.core.TelegramFlow
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author nvoxel
 */
val androidNetworkModule = module {
    includes(androidMappersModule)

    val telegramFlow = TelegramFlow().also { it.attachClient() }

    single<TelegramFlow> { telegramFlow }
    single<CommonTelegramFlow> { telegramFlow }

    single<AuthRepository> {
        AndroidAuthRepository(
            telegramFlow = get(),
            tdLibParametersMapper = get(),
        )
    }

    single<ChatsRepository> {
        AndroidChatsRepository(
            telegramFlow = get(),
        )
    }

    single<FilesRepository> {
        AndroidFilesRepository(
            telegramFlow = get(),
            messagesRepository = get(),
            fileMapper = get(),
            messageFileMapper = get(),
        )
    }

    single<LocalFilesRepository> {
        AndroidLocalFilesRepository(
            context = androidContext(),
        )
    }

    single<MessagesRepository> {
        AndroidMessagesRepository(
            telegramFlow = get(),
            messagesMapper = get(),
            chatsMapper = get(),
        )
    }

    single<ProfileRepository> {
        AndroidProfileRepository(
            telegramFlow = get(),
            userMapper = get(),
        )
    }

    single<SupergroupsRepository> {
        AndroidSupergroupsRepository(
            telegramFlow = get(),
            chatsMapper = get(),
            supergroupMapper = get(),
        )
    }
}
