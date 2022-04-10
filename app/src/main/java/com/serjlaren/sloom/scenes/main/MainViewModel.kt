package com.serjlaren.sloom.scenes.main

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.AppScreen
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.services.ResourcesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val resourcesService: ResourcesService,
) : BaseViewModel() {

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

    override fun start() {}

    override fun stop() {}

    fun playClicked() {
        navigateToScreen(AppScreen.GameSettings)
    }

    fun rulesClicked() {
        //TODO
    }

    fun aboutClicked() {
        //TODO
    }
}