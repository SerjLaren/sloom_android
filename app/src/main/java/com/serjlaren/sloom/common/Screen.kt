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
        class SourceCode(val url: String = "https://github.com/SerjLaren/sloom_android"): ExternalScreen()
        class AboutMe(val url: String = "https://about.me/serjlaren"): ExternalScreen()
    }
}