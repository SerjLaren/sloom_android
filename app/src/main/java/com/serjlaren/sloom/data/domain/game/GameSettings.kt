package com.serjlaren.sloom.data.domain.game

class GameSettings(
    val secondsPerMove: Int,
    val wordsCount: Int,
    val teamsCount: Int,
) {
    companion object {
        fun defaultSettings() = GameSettings(defaultSecondsPerMove, defaultWordsCount, defaultTeamsCount)

        const val minTeamsCount = 2
        const val maxTeamsCount = 4
        const val minWordsCount = 10
        const val maxWordsCount = 60
        const val minSecondsPerMove = 10
        const val maxSecondsPerMove = 60

        const val defaultTeamsCount = 2
        const val defaultWordsCount = 30
        const val defaultSecondsPerMove = 30
    }
}