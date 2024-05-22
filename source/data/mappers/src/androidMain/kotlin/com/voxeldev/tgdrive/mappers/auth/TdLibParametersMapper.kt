package com.voxeldev.tgdrive.mappers.auth

import com.voxeldev.tgdrive.auth.api.TdLibParameters
import org.drinkless.td.libcore.telegram.TdApi.TdlibParameters

/**
 * @author nvoxel
 */
class TdLibParametersMapper {

    fun toRequest(tdLibParameters: TdLibParameters): TdlibParameters =
        TdlibParameters(
            tdLibParameters.useTestDc,
            tdLibParameters.databaseDirectory,
            tdLibParameters.filesDirectory,
            tdLibParameters.useFileDatabase,
            tdLibParameters.useChatInfoDatabase,
            tdLibParameters.useMessageDatabase,
            tdLibParameters.useSecretChats,
            tdLibParameters.apiId,
            tdLibParameters.apiHash,
            tdLibParameters.systemLanguageCode,
            tdLibParameters.deviceModel,
            tdLibParameters.systemVersion,
            tdLibParameters.applicationVersion,
            tdLibParameters.enableStorageOptimizer,
            tdLibParameters.ignoreFileNames,
        )
}
