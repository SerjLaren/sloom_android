package com.serjlaren.sloom.scenes.game

import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment : BaseFragment<GameViewModel>(R.layout.fragment_game) {

    override val viewModel: GameViewModel by viewModels()
    override val viewBinding: ViewBinding by viewBinding()


}