package com.serjlaren.sloom.scenes.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnStart
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import com.serjlaren.sloom.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    override val viewBinding: FragmentMainBinding by viewBinding()

    private var scaleUpButtonsX = ObjectAnimator()
    private var scaleUpButtonsY = ObjectAnimator()

    override fun initViews() {
        super.initViews()
        with(viewBinding) {
            playClickableLayout.setOnClickListener { viewModel.playClicked() }
            rulesClickableLayout.setOnClickListener { viewModel.rulesClicked() }
            aboutClickableLayout.setOnClickListener { viewModel.aboutClicked() }

            scaleUpButtonsX = ObjectAnimator.ofFloat(buttonsLayout, View.SCALE_X, 0f, 1f)
                .apply { duration = 500 }
            scaleUpButtonsY = ObjectAnimator.ofFloat(buttonsLayout, View.SCALE_Y, 0f, 1f)
                .apply { duration = 500 }
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            with(viewBinding) {
                bindText(playButtonText, playTextView)
                bindText(rulesButtonText, rulesTextView)
                bindText(aboutButtonText, aboutTextView)
                bindCommand(startScreenAnimation) {
                    AnimatorSet().apply {
                        playTogether(scaleUpButtonsX, scaleUpButtonsY)
                        doOnStart {
                            buttonsLayout.visibility = View.VISIBLE
                        }
                    }.start()
                }
            }
        }
    }
}