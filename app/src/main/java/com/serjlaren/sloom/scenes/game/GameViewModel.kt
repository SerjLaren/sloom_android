package com.serjlaren.sloom.scenes.game

import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.services.GameService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameService: GameService,
) : BaseViewModel() {

    val currentWord = gameService.currentWordFlow

    override fun init() {

    }

    override fun start() {

    }

    override fun stop() {

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