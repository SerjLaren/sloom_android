package com.serjlaren.sloom.scenes.main

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import com.serjlaren.sloom.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    override val viewBinding: FragmentMainBinding by viewBinding()

    override fun initViews() {
        super.initViews()
        with(viewBinding) {
            playClickableLayout.setOnClickListener { viewModel.playClicked() }
            rulesClickableLayout.setOnClickListener { viewModel.rulesClicked() }
            aboutClickableLayout.setOnClickListener { viewModel.aboutClicked() }
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            with(viewBinding) {
                bindText(playButtonText, playTextView)
                bindText(rulesButtonText, rulesTextView)
                bindText(aboutButtonText, aboutTextView)
            }
        }
    }
}
