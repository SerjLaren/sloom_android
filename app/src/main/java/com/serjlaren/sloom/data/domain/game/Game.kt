package com.serjlaren.sloom.data.domain.game

import com.serjlaren.sloom.data.domain.teams.Team
import com.serjlaren.sloom.data.domain.words.Word

fun game(block: (Game.Builder.() -> Unit)? = null) = Game.Builder().apply { block?.invoke(this) }.build()

data class Game(
    val allWords: List<Word>,
    val teams: List<Team>,
    val guessedWords: List<Word>,
    val phase: GamePhase,
    val settings: GameSettings,
) {
    class Builder {
        var allWords = listOf<Word>()
        var teams = listOf<Team>()
        var guessedWords = listOf<Word>()
        var phase = GamePhase.First
        var settings = gameSettings()

        fun build() = Game(
            allWords = allWords,
            teams = teams,
            guessedWords = guessedWords,
            phase = phase,
            settings = settings,
        )
    }
}
