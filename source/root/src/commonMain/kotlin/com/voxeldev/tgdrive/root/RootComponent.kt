package com.voxeldev.tgdrive.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.voxeldev.tgdrive.auth.AuthComponent
import com.voxeldev.tgdrive.main.MainComponent

/**
 * @author nvoxel
 */
interface RootComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class AuthChild(val component: AuthComponent) : Child
        class MainChild(val component: MainComponent) : Child
    }
}
