package com.serjlaren.sloom.scenes.splash

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.Screen
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
    val gameNameText = Text()
    val startSplashAnimationCommand = Command()

    override fun init() {
        viewModelScope.launch {
            gameNameText.emitValueSuspend(resourcesService.getString(R.string.app_name))
            bySerjLarenText.emitValueSuspend(resourcesService.getString(R.string.by_serjlaren))
        }
    }

    override fun resume() {
        super.resume()
        viewModelScope.launch {
            startSplashAnimationCommand.emitCommandSuspend()
        }
    }

    fun onAnimationEnd() {
        navigateToScreen(Screen.AppScreen.Main)
    }
}