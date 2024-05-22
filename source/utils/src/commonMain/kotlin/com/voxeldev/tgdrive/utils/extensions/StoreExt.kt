package com.voxeldev.tgdrive.utils.extensions

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.rx.observer

/**
 * @see <a href="https://github.com/IlyaGulya/TodoAppDecomposeMviKotlin/blob/15d286546a97e5b616647ceb12613c306020a87a/common/utils/src/commonMain/kotlin/example/todo/common/utils/StoreExt.kt">Source</a>
 */
fun <T : Any> Store<*, T, *>.asValue(): Value<T> =
    object : Value<T>() {
        override val value: T get() = state

        override fun subscribe(observer: (T) -> Unit): Cancellation {
            val disposable = states(observer(onNext = observer))

            return Cancellation {
                disposable.dispose()
            }
        }
    }
