package com.serjlaren.sloom.data.domain.game

class GameSettings(
    val secondsPerMove: Int,
    val wordsCount: Int,
) {
    companion object {
        fun defaultSettings() = GameSettings(30, 30)
    }
}