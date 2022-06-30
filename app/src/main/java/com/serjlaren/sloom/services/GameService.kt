package com.serjlaren.sloom.services

import com.serjlaren.sloom.data.domain.game.*
import com.serjlaren.sloom.data.domain.teams.Team
import com.serjlaren.sloom.data.domain.teams.team
import com.serjlaren.sloom.data.domain.words.Word
import com.serjlaren.sloom.data.domain.words.WordLevel
import com.serjlaren.sloom.data.domain.words.WordTopic
import com.serjlaren.sloom.data.domain.words.word
import com.serjlaren.sloom.di.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameService @Inject constructor(
    @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    private val timerService: TimerService,
    private val audioService: AudioService,
) {

    private val currentGameStateFlowInternal = MutableSharedFlow<GameState>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentGameStateFlow = currentGameStateFlowInternal.asSharedFlow()

    val currentTimeFlow = timerService.timerFlow
        .onEach { time ->
            if (time == 0) {
                timeIsOut()
            }
        }

    val minTeamsCount = GameSettings.minTeamsCount
    val maxTeamsCount = GameSettings.maxTeamsCount
    val minWordsCount = GameSettings.minWordsCount
    val maxWordsCount = GameSettings.maxWordsCount
    val minSecondsPerMove = GameSettings.minSecondsPerMove
    val maxSecondsPerMove = GameSettings.maxSecondsPerMove
    val defaultTeamsCount = GameSettings.defaultTeamsCount
    val defaultWordsCount = GameSettings.defaultWordsCount
    val defaultSecondsPerMove = GameSettings.defaultSecondsPerMove
    val defaultWordTopic = GameSettings.defaultWordTopic

    private var currentGame = game()
    private var currentWord = word()
    private var currentTeam = team()
    private var moveInitialTeam = team()

    suspend fun initGame(teamsCount: Int, wordsCount: Int, secondsPerMove: Int, wordTopicsIndexes: List<Int>) = withContext(ioDispatcher) {
        timerService.stopTimer()
        gameSettings {
            this.teamsCount = teamsCount
            this.wordsCount = wordsCount
            this.secondsPerMove = secondsPerMove
            wordsTopics = wordTopicsIndexes.map { WordTopic.fromIndex(it) }
        }.let { gameSettings ->
            currentGame = game {
                allWords = TestWords.getTestWords()
                teams = createTeams(gameSettings.teamsCount)
                phase = GamePhase.First
                settings = gameSettings
            }
        }

        updateGameState(GameState.PhaseStarting(GamePhase.First))
    }

    fun startMove() {
        installNewWord()
        timerService.startTimer(currentGame.settings.secondsPerMove)
    }

    fun startPhase() {
        updateGameState(GameState.TeamMoveStarting(currentGame.teams[currentTeam.index]))
    }

    fun wordGuessed() {
        audioService.playGuessedSound()
        currentGame = currentGame.copy(
            teams = currentGame.teams.map { team -> if (team.index == currentTeam.index) team.copy(score = team.score + 1) else team },
            guessedWords = currentGame.guessedWords.toMutableList().apply { add(currentWord) },
        )
        if (currentGame.allWords.size == currentGame.guessedWords.size) {
            moveInitialTeam.nextTeam().let { team ->
                currentTeam = team
                moveInitialTeam = team
            }
            timerService.stopTimer()
            when (currentGame.phase) {
                GamePhase.First -> {
                    currentGame = currentGame.copy(
                        guessedWords = listOf(),
                        phase = GamePhase.Second,
                    )
                    updateGameState(GameState.PhaseStarting(GamePhase.Second))
                }
                GamePhase.Second -> {
                    currentGame = currentGame.copy(
                        guessedWords = listOf(),
                        phase = GamePhase.Third,
                    )
                    updateGameState(GameState.PhaseStarting(GamePhase.Third))
                }
                GamePhase.Third -> {
                    updateGameState(GameState.GameFinished(currentGame.teams))
                }
            }
        } else {
            installNewWord()
        }
    }

    private fun installNewWord() {
        currentWord = currentGame.let { game ->
            game.allWords.shuffled().first { game.guessedWords.contains(it).not() }
        }
        updateGameState(GameState.WordGuessing(currentTeam, currentWord))
    }

    private fun updateGameState(state: GameState) {
        currentGameStateFlowInternal.tryEmit(state)
    }

    private fun timeIsOut() {
        timerService.stopTimer()
        currentTeam = currentTeam.nextTeam()
        updateGameState(GameState.TeamMoveStarting(currentTeam))
    }

    private fun Team.nextTeam() =
        if (index == currentGame.teams.size - 1)
            currentGame.teams[0]
        else
            currentGame.teams[index + 1]

    private fun createTeams(teamsCount: Int): List<Team> {
        val teams = mutableListOf<Team>()
        repeat(teamsCount) { i ->
            teams.add(team {
                index = i
                number = i + 1
                score = 0
            })
        }
        moveInitialTeam = teams[0]
        return teams
    }
}

object TestWords{
    fun getTestWords() = listOf(
        Word("Кот", WordLevel.All, WordTopic.All),
        Word("Собака", WordLevel.All, WordTopic.All),
        Word("Дом", WordLevel.All, WordTopic.All),
        Word("Вода", WordLevel.All, WordTopic.All),
        Word("Солнце", WordLevel.All, WordTopic.All),
        Word("Стакан", WordLevel.All, WordTopic.All),
        Word("Ложка", WordLevel.All, WordTopic.All),
        Word("Футбол", WordLevel.All, WordTopic.All),
        Word("Игра", WordLevel.All, WordTopic.All),
        Word("Стул", WordLevel.All, WordTopic.All),
    )
}