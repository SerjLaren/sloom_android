package com.serjlaren.sloom.scenes.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import com.serjlaren.sloom.common.scaleObjectAnimators
import com.serjlaren.sloom.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    override val viewBinding: FragmentMainBinding by viewBinding()

    private var scaleUpButtonsAnim = listOf(ObjectAnimator())
    private var alphaLogoAnim = ObjectAnimator()

    override fun initViews() {
        super.initViews()
        with(viewBinding) {
            playClickableLayout.setOnClickListener { viewModel.playClicked() }
            rulesClickableLayout.setOnClickListener { viewModel.rulesClicked() }
            aboutClickableLayout.setOnClickListener { viewModel.aboutClicked() }

            scaleUpButtonsAnim = buttonsLayout.scaleObjectAnimators(0f, 1f, 500)
            alphaLogoAnim = ObjectAnimator.ofFloat(logoImageView, View.ALPHA, 0f, 1f).apply { duration = 500 }
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            with(viewBinding) {
                bindText(playButtonText, playTextView)
                bindText(rulesButtonText, rulesTextView)
                bindText(aboutButtonText, aboutTextView)
                bindCommand(startScreenAnimationCommand) {
                    AnimatorSet().apply {
                        playTogether(scaleUpButtonsAnim)
                        doOnStart {
                            buttonsLayout.visibility = View.VISIBLE
                        }
                        doOnEnd {
                            if (logoImageView.visibility != View.VISIBLE) {
                                alphaLogoAnim.apply {
                                    doOnStart {
                                        logoImageView.visibility = View.VISIBLE
                                    }
                                }.start()
                            }
                        }
                    }.start()
                }
            }
        }
    }
}