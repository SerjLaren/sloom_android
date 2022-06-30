package com.serjlaren.sloom.scenes.game

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.Screen
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.data.domain.game.GamePhase
import com.serjlaren.sloom.data.domain.game.GameState
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

    override var doubleBackToExit = true

    val currentTeam = Text()
    val currentWord = Text()
    val currentTime = Text()
    val nextPhase = Text()
    val phaseRules = Text()
    val nextTeamText = Text()
    val gameResultsText = Text()
    val guessedButtonText = Text()
    val startButtonText = Text()
    val finishButtonText = Text()
    val phaseStartingVisible = Visible()
    val teamMoveStartingVisible = Visible()
    val gameFinishedVisible = Visible()

    init {
        gameService.currentGameStateFlow.onEach { gameState ->
            processGameState(gameState)
        }.launchIn(viewModelScope)

        gameService.currentTimeFlow.onEach {
            currentTime.emitValueSuspend(it.toString())
        }.launchIn(viewModelScope)
    }

    override fun init() {
        viewModelScope.launch {
            guessedButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_game_btn_guessed))
            startButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_game_btn_start))
            finishButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_game_btn_game_finished))
        }
    }

    private suspend fun processGameState(gameState: GameState) {
        when (gameState) {
            is GameState.WordGuessing -> {
                currentTeam.emitValueSuspend(
                    resourcesService.getString(
                        R.string.scr_game_txt_team_f,
                        gameState.team.number.toString()
                    )
                )
                currentWord.emitValueSuspend(gameState.word.word)
            }
            is GameState.PhaseStarting -> {
                phaseRules.emitValueSuspend(
                    when (gameState.phase) {
                        GamePhase.First -> resourcesService.getString(R.string.scr_game_txt_phase_1_rules)
                        GamePhase.Second -> resourcesService.getString(R.string.scr_game_txt_phase_2_rules)
                        GamePhase.Third -> resourcesService.getString(R.string.scr_game_txt_phase_3_rules)
                    }
                )
                nextPhase.emitValueSuspend(
                    resourcesService.getString(
                        R.string.scr_game_txt_next_phase_f, when (gameState.phase) {
                            GamePhase.First -> resourcesService.getString(R.string.scr_game_txt_phase_1_rules)
                            GamePhase.Second -> resourcesService.getString(R.string.scr_game_txt_phase_2_rules)
                            GamePhase.Third -> resourcesService.getString(R.string.scr_game_txt_phase_3_rules)
                        }
                    )
                )
            }
            is GameState.TeamMoveStarting -> {
                nextTeamText.emitValueSuspend(
                    resourcesService.getString(
                        R.string.scr_game_txt_next_team_f,
                        gameState.team.number.toString()
                    )
                )
            }
            is GameState.GameFinished -> {
                gameResultsText.emitValueSuspend(
                    resourcesService.getString(
                        R.string.scr_game_txt_game_results,
                        gameState.teams.sortedByDescending { it.score }.map { team ->
                            "${
                                resourcesService.getString(
                                    R.string.scr_game_txt_team_f,
                                    team.number.toString()
                                )
                            } - ${team.score}"
                        }.joinToString("\n"))
                )
            }
            is GameState.Initialization -> {}
        }

        phaseStartingVisible.emitValueSuspend(gameState is GameState.PhaseStarting)
        teamMoveStartingVisible.emitValueSuspend(gameState is GameState.TeamMoveStarting)
        gameFinishedVisible.emitValueSuspend(gameState is GameState.GameFinished)
    }

    fun wordGuessedClicked() {
        gameService.wordGuessed()
    }

    fun startPhaseClicked() {
        gameService.startPhase()
    }

    fun startTeamMoveClicked() {
        gameService.startMove()
    }

    fun finishGameButtonClicked() {
        navigateToScreen(Screen.AppScreen.Main)
    }
}