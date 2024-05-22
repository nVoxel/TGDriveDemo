package com.voxeldev.tgdrive.auth.api

/**
 * @author nvoxel
 */
data class TdLibParameters(
    val useTestDc: Boolean,
    val databaseDirectory: String,
    val filesDirectory: String,
    val useFileDatabase: Boolean,
    val useChatInfoDatabase: Boolean,
    val useMessageDatabase: Boolean,
    val useSecretChats: Boolean,
    val apiId: Int,
    val apiHash: String,
    val systemLanguageCode: String,
    val deviceModel: String,
    val systemVersion: String?,
    val applicationVersion: String,
    val enableStorageOptimizer: Boolean,
    val ignoreFileNames: Boolean,
)
