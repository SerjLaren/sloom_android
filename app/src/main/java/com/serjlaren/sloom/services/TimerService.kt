package com.serjlaren.sloom.services

import com.serjlaren.sloom.di.CoroutineScopeIO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TimerService @Inject constructor(
    @CoroutineScopeIO private val ioScope: CoroutineScope,
) {

    private val timerFlowInternal = MutableSharedFlow<Int>()
    val timerFlow = timerFlowInternal.asSharedFlow()

    private var timerJob: Job? = null

    fun startTimer(seconds: Int) {
        timerJob?.cancel()
        timerJob = ioScope.launch {
            (seconds - 1 downTo 0).asFlow()
                .cancellable()
                .onEach { delay(1000) }
                .onStart { timerFlowInternal.emit(seconds) }
                .conflate()
                .map { timerFlowInternal.emit(it) }
                .collect()
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}