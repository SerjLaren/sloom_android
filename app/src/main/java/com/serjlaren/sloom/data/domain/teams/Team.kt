package com.serjlaren.sloom.data.domain.teams

fun team(block: (Team.Builder.() -> Unit)? = null) = Team.Builder().apply { block?.invoke(this) }.build()

data class Team(
    val index: Int,
    val number: Int,
    val score: Int,
) {
    class Builder {
        var index = 0
        var number = 1
        var score = 0

        fun build() = Team(index, number, score)
    }
}