package com.serjlaren.sloom.scenes.splash

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.AppScreen
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.services.ResourcesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val resourcesService: ResourcesService,
) : BaseViewModel() {

    val bySerjLarenText = Text()
    val startSplashAnimation = Command()

    override fun init() {
        viewModelScope.launch {
            bySerjLarenText.emitValueSuspend(resourcesService.getString(R.string.by_serjlaren))
        }
    }

    override fun start() {
        viewModelScope.launch {
            startSplashAnimation.emitCommandSuspend()
        }
    }

    override fun stop() {}

    fun onAnimationEnd() {
        navigateToScreen(AppScreen.Main)
    }
}