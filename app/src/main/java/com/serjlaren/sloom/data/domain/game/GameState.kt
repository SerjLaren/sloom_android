package com.serjlaren.sloom.data.domain.game

import com.serjlaren.sloom.data.domain.teams.Team
import com.serjlaren.sloom.data.domain.words.Word

sealed class GameState {
    object Initialization : GameState()
    class WordGuessing(val team: Team, val word: Word) : GameState()
    class PhaseStarting(val phase: GamePhase) : GameState()
    class TeamMoveStarting(val team: Team) : GameState()
    class GameFinished(val teams: List<Team>) : GameState()
}
