//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.mappers.media.FileMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.UpdateFileGenerationStart

/**
 * emits [File] if information about a file was updated.
 */
fun TelegramFlow.fileFlow(fileMapper: FileMapper): Flow<File> = this.getUpdatesFlowOfType<TdApi.UpdateFile>()
    .mapNotNull { fileMapper.toResponse(it.file) }

/**
 * emits [UpdateFileGenerationStart] if the file generation process needs to be started by the
 * application.
 */
fun TelegramFlow.fileGenerationStartFlow(): Flow<UpdateFileGenerationStart> =
    this.getUpdatesFlowOfType()

/**
 * emits generationId [Long] if file generation is no longer needed.
 */
fun TelegramFlow.fileGenerationStopFlow(): Flow<Long> =
    this.getUpdatesFlowOfType<TdApi.UpdateFileGenerationStop>()
        .mapNotNull { it.generationId }
