package com.serjlaren.sloom.scenes.game.gamesettings

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.services.GameService
import com.serjlaren.sloom.services.ResourcesService
import kotlinx.coroutines.launch

class GameSettingsViewModel(
    private val resourcesService: ResourcesService,
    private val gameService: GameService,
) : BaseViewModel() {

    val playButtonText = Text()
    val teamsCountTitleText = Text()
    val wordsCountTitleText = Text()
    val secondsPerMoveTitleText = Text()
    val minTeamsCount = Data<Int>()
    val maxTeamsCount = Data<Int>()
    val minWordsCount = Data<Int>()
    val maxWordsCount = Data<Int>()
    val minSecondsPerMove = Data<Int>()
    val maxSecondsPerMove = Data<Int>()
    val selectedTeamsCount = Data<Int>()
    val selectedWordsCount = Data<Int>()
    val selectedSecondsPerMove = Data<Int>()
    val applyRanges = Command()

    override fun init() {
        viewModelScope.launch {
            playButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_btn_play))
            teamsCountTitleText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_txt_teams_count))
            wordsCountTitleText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_txt_words_count))
            secondsPerMoveTitleText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_txt_seconds_per_move))
            minTeamsCount.emitValueSuspend(gameService.minTeamsCount)
            maxTeamsCount.emitValueSuspend(gameService.maxTeamsCount)
            minWordsCount.emitValueSuspend(gameService.minWordsCount)
            maxWordsCount.emitValueSuspend(gameService.maxWordsCount)
            minSecondsPerMove.emitValueSuspend(gameService.minSecondsPerMove)
            maxSecondsPerMove.emitValueSuspend(gameService.maxSecondsPerMove)
            applyRanges.emitCommandSuspend()
            selectedTeamsCount.emitValueSuspend(gameService.defaultTeamsCount)
            selectedWordsCount.emitValueSuspend(gameService.defaultWordsCount)
            selectedSecondsPerMove.emitValueSuspend(gameService.defaultSecondsPerMove)
        }
    }

    fun playClicked() {

    }
}