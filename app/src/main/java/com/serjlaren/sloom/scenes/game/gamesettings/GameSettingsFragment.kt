package com.serjlaren.sloom.scenes.game.gamesettings

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import com.serjlaren.sloom.databinding.FragmentGameSettingsBinding

class GameSettingsFragment : BaseFragment(R.layout.fragment_game_settings) {

    override val viewModel: GameSettingsViewModel by viewModels()
    override val viewBinding: FragmentGameSettingsBinding by viewBinding()


}