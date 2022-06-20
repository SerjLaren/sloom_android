package com.serjlaren.sloom.data.domain.teams

data class Team(
    val index: Int,
    val number: Int,
    val score: Int,
) {
    companion object {
        fun defaultTeam() = Team(0, 1, 0)
    }
}