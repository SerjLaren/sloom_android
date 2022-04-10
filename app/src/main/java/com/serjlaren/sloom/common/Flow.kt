package com.serjlaren.sloom.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

typealias TextFlow = SharedFlow<String>
typealias VisibleFlow = SharedFlow<Boolean>
typealias EnabledFlow = SharedFlow<Boolean>
typealias DataFlow<T> = SharedFlow<T>
typealias CommandFlow = SharedFlow<Unit>

fun <T> Flow<T>.launchWhenStarted(lifecycleOwner: LifecycleOwner, lifecycleScope: LifecycleCoroutineScope) {
    lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            this@launchWhenStarted.collect()
        }
    }
}