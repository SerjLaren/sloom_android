package com.serjlaren.sloom.data.domain.game

import com.serjlaren.sloom.data.domain.words.WordTopic

class GameSettings(
    val secondsPerMove: Int,
    val wordsCount: Int,
    val teamsCount: Int,
    val wordsTopics: List<WordTopic>,
) {
    companion object {
        fun defaultSettings() = GameSettings(defaultSecondsPerMove, defaultWordsCount, defaultTeamsCount, listOf(defaultWordTopic))

        const val minTeamsCount = 2
        const val maxTeamsCount = 4
        const val minWordsCount = 10
        const val maxWordsCount = 60
        const val minSecondsPerMove = 10
        const val maxSecondsPerMove = 60

        const val defaultTeamsCount = 2
        const val defaultWordsCount = 30
        const val defaultSecondsPerMove = 30
        val defaultWordTopic = WordTopic.All
    }
}