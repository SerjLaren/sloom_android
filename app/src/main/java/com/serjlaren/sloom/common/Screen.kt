package com.serjlaren.sloom.common

sealed class Screen {

    sealed class AppScreen : Screen() {
        object Splash : AppScreen()
        object Main : AppScreen()
        object GameSettings : AppScreen()
        object Game : AppScreen()
        object Rules: AppScreen()
        object About: AppScreen()
    }

    sealed class ExternalScreen : Screen() {
        object SourceCode: ExternalScreen()
        object AboutMe: ExternalScreen()
    }
}