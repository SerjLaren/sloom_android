package com.serjlaren.sloom.common.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.common.AppScreen
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val navigateToScreen = TCommand<AppScreen>()
    val showToast = TCommand<String>()

    abstract fun init()
    open fun start() {}
    open fun stop() {}
    open fun resume() {}
    open fun pause() {}

    protected fun navigateToScreen(appScreen: AppScreen) = viewModelScope.launch {
        navigateToScreen.emitValueSuspend(appScreen)
    }

    protected fun showToast(message: String) = viewModelScope.launch {
        showToast.emitValueSuspend(message)
    }

    protected fun Text(): ViewModelSharedFlow<String> = ViewModelSharedFlowImpl()
    protected fun Visible(): ViewModelSharedFlow<Boolean> = ViewModelSharedFlowImpl()
    protected fun Enabled(): ViewModelSharedFlow<Boolean> = ViewModelSharedFlowImpl()
    protected fun <T> Data(): ViewModelSharedFlow<T> = ViewModelSharedFlowImpl()
    protected fun Command(): ViewModelSharedFlow<Unit> = ViewModelSharedFlowImpl(savesData = false)
    protected fun <T> TCommand(): ViewModelSharedFlow<T> = ViewModelSharedFlowImpl(savesData = false)

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
    val savesData: Boolean = true,
    val wrapped: MutableSharedFlow<T> = MutableSharedFlow(
        replay = if (savesData) 1 else 0,
        extraBufferCapacity =  0,
        onBufferOverflow = if (savesData) BufferOverflow.DROP_OLDEST else BufferOverflow.SUSPEND),
) : ViewModelSharedFlow<T>(wrapped)