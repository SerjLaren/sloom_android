package com.serjlaren.sloom.scenes.game.gamesettings

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnStart
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import com.serjlaren.sloom.common.scaleObjectAnimators
import com.serjlaren.sloom.databinding.FragmentGameSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameSettingsFragment : BaseFragment<GameSettingsViewModel>(R.layout.fragment_game_settings) {

    override val viewModel: GameSettingsViewModel by viewModels()
    override val viewBinding: FragmentGameSettingsBinding by viewBinding()

    private var scaleUpScreenAnim = listOf(ObjectAnimator())

    override fun initViews() {
        super.initViews()
        with(viewBinding) {
            playClickableLayout.setOnClickListener {
                viewModel.playClicked(
                    teamsCount = teamsCountLayout.getRangeValue(),
                    wordsCount = wordsCountLayout.getRangeValue(),
                    secondsPerMove = timePerMoveLayout.getRangeValue(),
                    wordTopicsIndexes = wordsTopicsLayout.getCheckedIndexes(),
                )
            }

            scaleUpScreenAnim = mainLayout.scaleObjectAnimators(0f, 1f, 500)
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()

        with(viewBinding) {
            with(viewModel) {
                bindText(teamsCountTitleText, teamsCountLayout.titleTextView)
                bindText(wordsCountTitleText, wordsCountLayout.titleTextView)
                bindText(secondsPerMoveTitleText, timePerMoveLayout.titleTextView)
                bindText(playButtonText, playTextView)
                bindData(minTeamsCount) { teamsCountLayout.setRangeStartValue(it) }
                bindData(maxTeamsCount) { teamsCountLayout.setRangeEndValue(it) }
                bindData(minWordsCount) { wordsCountLayout.setRangeStartValue(it) }
                bindData(maxWordsCount) { wordsCountLayout.setRangeEndValue(it) }
                bindData(minSecondsPerMove) { timePerMoveLayout.setRangeStartValue(it) }
                bindData(maxSecondsPerMove) { timePerMoveLayout.setRangeEndValue(it) }
                bindData(selectedTeamsCount) { teamsCountLayout.setRangeValue(it) }
                bindData(selectedWordsCount) { wordsCountLayout.setRangeValue(it) }
                bindData(selectedSecondsPerMove) { timePerMoveLayout.setRangeValue(it) }
                bindData(wordsTopics) { wordsTopicsLayout.setCheckBoxes(it) }
                bindData(selectedWordTopic) { wordsTopicsLayout.setChecked(it, true) }
                bindCommand(applyRangesCommand) {
                    teamsCountLayout.applyRange()
                    wordsCountLayout.applyRange()
                    timePerMoveLayout.applyRange()
                }
                bindCommand(startScreenAnimationCommand) {
                    AnimatorSet().apply {
                        playTogether(scaleUpScreenAnim)
                        doOnStart {
                            mainLayout.visibility = View.VISIBLE
                        }
                    }.start()
                }
            }
        }
    }
}