package com.serjlaren.sloom.scenes.game

import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : BaseFragment<GameViewModel>(R.layout.fragment_game) {

    override val viewModel: GameViewModel by viewModels()
    override val viewBinding: FragmentGameBinding by viewBinding()

    override fun initViews() {
        super.initViews()
        with(viewBinding) {
            wordGuessedClickableLayout.setOnClickListener {
                viewModel.wordGuessedClicked()
            }
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()

        with(viewBinding) {
            with(viewModel) {
                bindText(currentTeam, moveTeamTextView)
                bindText(currentTime, moveSecondsTextView)
                bindText(currentWord, currentWordTextView)
            }
        }
    }
}