package com.serjlaren.sloom.common.mvvm

import androidx.lifecycle.ViewModel
import com.serjlaren.sloom.common.AppScreen
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel : ViewModel() {

    val navigateToScreen = Data<AppScreen>()

    abstract fun init()
    abstract fun start()
    abstract fun stop()

    protected fun navigateToScreen(appScreen: AppScreen) = navigateToScreen.emitValue(appScreen)

    protected fun Text(): ViewModelSharedFlow<String> = ViewModelSharedFlowImpl()
    protected fun Visible(): ViewModelSharedFlow<Boolean> = ViewModelSharedFlowImpl()
    protected fun Enabled(): ViewModelSharedFlow<Boolean> = ViewModelSharedFlowImpl()
    protected fun <T> Data(): ViewModelSharedFlow<T> = ViewModelSharedFlowImpl()
    protected fun Command(): ViewModelSharedFlow<Unit> = ViewModelSharedFlowImpl()

    protected fun <T> ViewModelSharedFlow<T>.emitValue(value: T) = when (this) {
        is ViewModelSharedFlowImpl -> wrapped.tryEmit(value)
    }

    protected suspend fun <T> ViewModelSharedFlow<T>.emitValueSuspend(value: T) = when (this) {
        is ViewModelSharedFlowImpl -> wrapped.emit(value)
    }

    protected fun ViewModelSharedFlow<Unit>.emitCommand() = when (this) {
        is ViewModelSharedFlowImpl -> wrapped.tryEmit(Unit)
    }

    protected suspend fun ViewModelSharedFlow<Unit>.emitCommandSuspend() = when (this) {
        is ViewModelSharedFlowImpl -> wrapped.emit(Unit)
    }
}

sealed class ViewModelSharedFlow<T>(sharedFlow: SharedFlow<T>) :
    SharedFlow<T> by sharedFlow

private class ViewModelSharedFlowImpl<T>(
    val wrapped: MutableSharedFlow<T> = MutableSharedFlow(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
) : ViewModelSharedFlow<T>(wrapped)