package com.serjlaren.sloom.services

import com.serjlaren.sloom.data.domain.game.Game
import com.serjlaren.sloom.data.domain.game.GamePhase
import com.serjlaren.sloom.data.domain.game.GameSettings
import com.serjlaren.sloom.data.domain.teams.Team
import com.serjlaren.sloom.data.domain.words.Word
import com.serjlaren.sloom.data.domain.words.WordLevel
import com.serjlaren.sloom.data.domain.words.WordTopic
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
) {

    private val currentWordFlowInternal = MutableSharedFlow<Word>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentWordFlow = currentWordFlowInternal.asSharedFlow()

    private val currentTeamFlowInternal = MutableSharedFlow<Team>(replay = 1, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentTeamFlow = currentTeamFlowInternal.asSharedFlow()

    private val newMoveFlowInternal = MutableSharedFlow<Team>(replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
    val newMoveFlow = newMoveFlowInternal.asSharedFlow()

    private val newPhaseFlowInternal = MutableSharedFlow<GamePhase>(replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
    val newPhaseFlow = newPhaseFlowInternal.asSharedFlow()

    private val endGameFlowInternal = MutableSharedFlow<List<Team>>(replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)
    val endGameFlow = endGameFlowInternal.asSharedFlow()

    val currentTimeFlow = timerService.timerFlow
        .onCompletion {
            timeIsOut()
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

    private var currentGame = Game.defaultGame()
    private var currentWord = Word.defaultWord()
    private var currentTeamNumber = 0

    suspend fun initGame(teamsCount: Int, wordsCount: Int, secondsPerMove: Int, wordTopicsIndexes: List<Int>) = withContext(ioDispatcher) {
        GameSettings(
            teamsCount = teamsCount,
            wordsCount = wordsCount,
            secondsPerMove = secondsPerMove,
            wordsTopics = wordTopicsIndexes.map { WordTopic.fromIndex(it) }
        ).let { gameSettings ->
            currentGame = currentGame.copy(
                allWords = TestWords.getTestWords(),
                teams = createTeams(gameSettings),
                guessedWords = listOf(),
                phase = GamePhase.First,
                settings = gameSettings
            )
        }
    }

    private fun createTeams(settings: GameSettings): List<Team> {
        val teams = mutableListOf<Team>()
        for (i in 0 until settings.teamsCount) {
            teams.add(Team(i, 0))
        }
        return teams
    }

    fun startMove() {
        installNewWord()
        timerService.startTimer(currentGame.settings.secondsPerMove)
    }

    fun startPhase() {
        installNewWord()
        timerService.startTimer(currentGame.settings.secondsPerMove)
    }

    fun wordGuessed() {
        currentGame = currentGame.copy(
            teams = currentGame.teams.map { team -> if (team.number == currentTeamNumber) team.copy(score = team.score + 1) else team }
        )
        if (currentGame.allWords.size == currentGame.guessedWords.size) {
            timerService.stopTimer()
            when (currentGame.phase) {
                GamePhase.First -> {
                    currentGame = currentGame.copy(
                        guessedWords = listOf(),
                        phase = GamePhase.Second,
                    )
                }
                GamePhase.Second -> {
                    currentGame = currentGame.copy(
                        guessedWords = listOf(),
                        phase = GamePhase.Third,
                    )
                }
                GamePhase.Third -> {
                    //TODO
                }
            }
        } else {
            currentGame = currentGame.copy(guessedWords = currentGame.guessedWords.toMutableList().apply { add(currentWord) })
            installNewWord()
        }
    }

    private fun installNewWord() {
        currentWord = currentGame.let { game ->
            currentGameAllWords().first { game.guessedWords.contains(it).not() }
        }
        currentWordFlowInternal.tryEmit(currentWord)
    }

    private fun currentGameAllWords() = currentGame.allWords.shuffled()

    private fun timeIsOut() {
        currentTeamNumber = if (currentTeamNumber == currentGame.teams.size - 1) 0 else currentTeamNumber + 1
        currentTeamFlowInternal.tryEmit(currentGame.teams.first { it.number == currentTeamNumber })
    }
}

object TestWords{
    fun getTestWords() = listOf(
        Word("Cat", WordLevel.All, WordTopic.All),
        Word("Dog", WordLevel.All, WordTopic.All),
        Word("House", WordLevel.All, WordTopic.All),
        Word("Water", WordLevel.All, WordTopic.All),
        Word("Sun", WordLevel.All, WordTopic.All),
        Word("Glass", WordLevel.All, WordTopic.All),
        Word("Spoon", WordLevel.All, WordTopic.All),
        Word("Football", WordLevel.All, WordTopic.All),
        Word("Round", WordLevel.All, WordTopic.All),
        Word("Game", WordLevel.All, WordTopic.All),
    )
}