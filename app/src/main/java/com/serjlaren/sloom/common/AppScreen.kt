package com.serjlaren.sloom.common

sealed class AppScreen {
    object Splash : AppScreen()
    object Main : AppScreen()
    object GameSettings : AppScreen()
    class Game() : AppScreen()
    object Rules: AppScreen()
    object About: AppScreen()
}