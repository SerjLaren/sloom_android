package com.serjlaren.sloom.data.domain.game

import com.serjlaren.sloom.data.domain.teams.Team
import com.serjlaren.sloom.data.domain.words.Word

data class Game(
    val allWords: List<Word>,
    val teams: List<Team>,
    val guessedWords: List<Word>,
    val phase: GamePhase,
    val settings: GameSettings,
) {
    companion object {
        fun defaultGame() = Game(
            allWords = listOf(),
            teams = listOf(),
            guessedWords = listOf(),
            phase = GamePhase.First,
            settings = GameSettings.defaultSettings(),
        )
    }
}