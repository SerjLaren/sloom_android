package com.serjlaren.sloom.scenes.main

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.Screen
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.services.ResourcesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourcesService: ResourcesService,
) : BaseViewModel() {

    override var doubleBackToExit = true

    val playButtonText = Text()
    val rulesButtonText = Text()
    val aboutButtonText = Text()

    override fun init() {
        viewModelScope.launch {
            playButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_main_btn_play))
            rulesButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_main_btn_rules))
            aboutButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_main_btn_about))
        }
    }

    fun playClicked() {
        navigateToScreen(Screen.AppScreen.GameSettings)
    }

    fun rulesClicked() {
        navigateToScreen(Screen.AppScreen.Rules)
    }

    fun aboutClicked() {
        navigateToScreen(Screen.AppScreen.About)
    }
}
