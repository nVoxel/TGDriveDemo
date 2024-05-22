package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.main.api.profile.ProfileRepository
import com.voxeldev.tgdrive.mappers.user.UserMapper
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getMe
import kotlinx.telegram.coroutines.getUser
import kotlinx.telegram.coroutines.logOut

/**
 * @author nvoxel
 */
internal class DesktopProfileRepository(
    private val telegramFlow: TelegramFlow,
    private val userMapper: UserMapper,
) : ProfileRepository {

    override suspend fun getMe() = userMapper.toResponse(user = telegramFlow.getMe())

    override suspend fun getUser(userId: Long) = userMapper.toResponse(user = telegramFlow.getUser(userId = userId))

    override suspend fun logOut() = telegramFlow.logOut()
}
