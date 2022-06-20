package com.serjlaren.sloom.common.mvvm

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.Screen
import com.serjlaren.sloom.common.mvvm.models.AlertDialogModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel : ViewModel() {

    val navigateToScreenCommand = TCommand<Screen>()
    val navigateBackCommand = Command()
    val showToastCommand = TCommand<String>()
    val showToastFromResourcesCommand = TCommand<Int>()
    val showAlertCommand = TCommand<AlertDialogModel>()

    abstract fun init()
    open fun start() {}
    open fun stop() {}
    open fun resume() {}
    open fun pause() {}

    open var doubleBackToExit = false

    private var doubleBackToExitPressed = false

    fun backButtonPressed() {
        if (doubleBackToExit) {
            if (doubleBackToExitPressed) {
                navigateBack()
                return
            }

            doubleBackToExitPressed = true
            showToastFromResources(R.string.scr_any_msg_press_back_again_to_exit)

            viewModelScope.launch {
                delay(2000)
                doubleBackToExitPressed = false
            }
        } else {
            navigateBack()
        }
    }

    protected fun navigateToScreen(screen: Screen) = viewModelScope.launch {
        navigateToScreenCommand.emitValueSuspend(screen)
    }

    protected fun navigateBack() = viewModelScope.launch {
        navigateBackCommand.emitCommandSuspend()
    }

    protected fun showToast(message: String) = viewModelScope.launch {
        showToastCommand.emitValueSuspend(message)
    }

    protected fun showToastFromResources(@StringRes messageId: Int) = viewModelScope.launch {
        showToastFromResourcesCommand.emitValueSuspend(messageId)
    }

    protected fun showAlertDialog(model: AlertDialogModel) = viewModelScope.launch {
        showAlertCommand.emitValueSuspend(model)
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