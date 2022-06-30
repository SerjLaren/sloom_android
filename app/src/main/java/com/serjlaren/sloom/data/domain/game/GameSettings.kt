package com.serjlaren.sloom.data.domain.game

import com.serjlaren.sloom.data.domain.words.WordTopic

fun gameSettings(block: (GameSettings.Builder.() -> Unit)? = null) =
    GameSettings.Builder().apply { block?.invoke(this) }.build()

class GameSettings(
    val secondsPerMove: Int,
    val wordsCount: Int,
    val teamsCount: Int,
    val wordsTopics: List<WordTopic>,
) {
    class Builder {
        var secondsPerMove = defaultSecondsPerMove
        var wordsCount = defaultWordsCount
        var teamsCount = defaultTeamsCount
        var wordsTopics = listOf(defaultWordTopic)

        fun build() = GameSettings(
            secondsPerMove = secondsPerMove,
            wordsCount = wordsCount,
            teamsCount = teamsCount,
            wordsTopics = wordsTopics,
        )
    }

    companion object {
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