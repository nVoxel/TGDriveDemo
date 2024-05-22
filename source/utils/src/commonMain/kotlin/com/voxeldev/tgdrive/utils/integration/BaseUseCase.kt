package com.voxeldev.tgdrive.utils.integration

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * @author nvoxel
 */
@OptIn(DelicateCoroutinesApi::class)
abstract class BaseUseCase<in Params, out Type>(
    private val asyncDispatcher: CoroutineDispatcher = Dispatchers.IO,
) where Type : Any {

    abstract suspend fun run(params: Params): Type

    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        asyncDispatcher: CoroutineDispatcher = this.asyncDispatcher,
        onResult: suspend (Result<Type>) -> Unit = {},
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(asyncDispatcher) {
                runCatching { run(params = params) }
            }
            onResult(deferred.await())
        }
    }

    object NoParams
}
