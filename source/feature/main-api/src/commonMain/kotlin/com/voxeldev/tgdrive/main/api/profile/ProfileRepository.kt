package com.voxeldev.tgdrive.main.api.profile

/**
 * @author nvoxel
 */
interface ProfileRepository {

    suspend fun getMe(): Profile

    suspend fun getUser(userId: Long): Profile

    suspend fun logOut()
}
