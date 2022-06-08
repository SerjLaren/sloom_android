package com.serjlaren.sloom.scenes.game

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.services.GameService
import com.serjlaren.sloom.services.ResourcesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameService: GameService,
    private val resourcesService: ResourcesService,
) : BaseViewModel() {

    val currentTeam = Text()
    val currentWord = Text()
    val currentTime = Text()

    init {
        gameService.currentTeamFlow.onEach {
            currentTeam.emitValueSuspend(resourcesService.getString(R.string.scr_game_txt_move_team_f, it.number))
        }.launchIn(viewModelScope)

        gameService.currentWordFlow.onEach {
            currentWord.emitValueSuspend(it.word)
        }.launchIn(viewModelScope)

        gameService.currentTimeFlow.onEach {
            currentTime.emitValueSuspend(it.toString())
        }.launchIn(viewModelScope)
    }

    override fun init() {

    }

    fun wordGuessedClicked() {
        gameService.wordGuessed()
    }

    fun startPhaseClicked() {
        gameService.startPhase()
    }

    fun startMoveClicked() {
        gameService.startMove()
    }
}