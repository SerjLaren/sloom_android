package com.serjlaren.sloom.common.mvvm

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    abstract fun init()
    abstract fun start()
    abstract fun stop()
}