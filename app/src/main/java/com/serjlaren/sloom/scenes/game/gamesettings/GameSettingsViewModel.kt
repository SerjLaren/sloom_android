package com.serjlaren.sloom.scenes.game.gamesettings

import androidx.lifecycle.viewModelScope
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.Screen
import com.serjlaren.sloom.common.mvvm.BaseViewModel
import com.serjlaren.sloom.data.domain.words.WordTopic
import com.serjlaren.sloom.services.GameService
import com.serjlaren.sloom.services.ResourcesService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSettingsViewModel @Inject constructor(
    private val resourcesService: ResourcesService,
    private val gameService: GameService,
) : BaseViewModel() {

    val playButtonText = Text()
    val teamsCountTitleText = Text()
    val wordsCountTitleText = Text()
    val secondsPerMoveTitleText = Text()
    val minTeamsCount = Data<Int>()
    val maxTeamsCount = Data<Int>()
    val minWordsCount = Data<Int>()
    val maxWordsCount = Data<Int>()
    val minSecondsPerMove = Data<Int>()
    val maxSecondsPerMove = Data<Int>()
    val selectedTeamsCount = Data<Int>()
    val selectedWordsCount = Data<Int>()
    val selectedSecondsPerMove = Data<Int>()
    val wordsTopics = Data<List<String>>()
    val selectedWordTopic = Data<Int>()
    val applyRangesCommand = Command()

    private val wordTopicsNames =
        WordTopic.values().map { enumValue ->
            when (enumValue) {
                WordTopic.All -> resourcesService.getString(R.string.scr_any_txt_word_topic_all)
                WordTopic.Animal -> resourcesService.getString(R.string.scr_any_txt_word_topic_animal)
                WordTopic.Person -> resourcesService.getString(R.string.scr_any_txt_word_topic_person)
                WordTopic.Profession -> resourcesService.getString(R.string.scr_any_txt_word_topic_profession)
                WordTopic.Action -> resourcesService.getString(R.string.scr_any_txt_word_topic_action)
                WordTopic.Clothes -> resourcesService.getString(R.string.scr_any_txt_word_topic_clothes)
                WordTopic.Transport -> resourcesService.getString(R.string.scr_any_txt_word_topic_transport)
                WordTopic.Bible -> resourcesService.getString(R.string.scr_any_txt_word_topic_bible)
            }
        }

    override fun init() {
        viewModelScope.launch {
            playButtonText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_btn_play))
            teamsCountTitleText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_txt_teams_count))
            wordsCountTitleText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_txt_words_count))
            secondsPerMoveTitleText.emitValueSuspend(resourcesService.getString(R.string.scr_game_settings_txt_seconds_per_move))
            minTeamsCount.emitValueSuspend(gameService.minTeamsCount)
            maxTeamsCount.emitValueSuspend(gameService.maxTeamsCount)
            minWordsCount.emitValueSuspend(gameService.minWordsCount)
            maxWordsCount.emitValueSuspend(gameService.maxWordsCount)
            minSecondsPerMove.emitValueSuspend(gameService.minSecondsPerMove)
            maxSecondsPerMove.emitValueSuspend(gameService.maxSecondsPerMove)
            wordsTopics.emitValueSuspend(wordTopicsNames)
        }
    }

    override fun resume() {
        super.resume()
        viewModelScope.launch {
            applyRangesCommand.emitCommandSuspend()
            selectedTeamsCount.emitValueSuspend(gameService.defaultTeamsCount)
            selectedWordsCount.emitValueSuspend(gameService.defaultWordsCount)
            selectedSecondsPerMove.emitValueSuspend(gameService.defaultSecondsPerMove)
            selectedWordTopic.emitValueSuspend(gameService.defaultWordTopic.ordinal)
        }
    }

    fun playClicked(teamsCount: Int, wordsCount: Int, secondsPerMove: Int, wordTopicsIndexes: List<Int>) {
        if (wordTopicsIndexes.isEmpty()) {
            showToast(resourcesService.getString(R.string.scr_game_settings_msg_fill_words_topics))
            return
        }

        viewModelScope.launch {
            gameService.initGame(teamsCount, wordsCount, secondsPerMove, wordTopicsIndexes)
            navigateToScreen(Screen.AppScreen.Game)
        }
    }
}