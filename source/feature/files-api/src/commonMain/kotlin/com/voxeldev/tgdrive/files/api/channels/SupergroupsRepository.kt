package com.voxeldev.tgdrive.files.api.channels

import com.voxeldev.tgdrive.main.api.chats.Chat

/**
 * @author nvoxel
 */
interface SupergroupsRepository {

    suspend fun createChannel(title: String, description: String) : Chat

    suspend fun getSupergroup(supergroupId : Long) : Supergroup

    suspend fun getSupergroupFullInfo(supergroupId : Long) : SupergroupFullInfo

    suspend fun renameSupergroup(chatId: Long, title: String)

    suspend fun deleteSupergroup(chatId: Long)
}
