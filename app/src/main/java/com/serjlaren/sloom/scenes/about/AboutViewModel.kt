package com.serjlaren.sloom.scenes.about

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.Screen
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.services.ResourcesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val resourcesService: ResourcesService,
) : BaseViewModel() {

    val aboutText = Text()
    val sourceCodeButtonText = Text()
    val aboutMeButtonText = Text()

    override fun init() {
        viewModelScope.launch {
            aboutText.emitValueSuspend(resourcesService.getString(R.string.scr_about_txt_about))
            sourceCodeButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_about_btn_source_code))
            aboutMeButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_about_btn_about_me))
        }
    }

    fun sourceCodeButtonClicked() {
        navigateToScreen(Screen.ExternalScreen.SourceCode())
    }

    fun aboutMeButtonClicked() {
        navigateToScreen(Screen.ExternalScreen.AboutMe())
    }
}