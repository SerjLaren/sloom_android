package com.serjlaren.sloom.scenes.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.fragment.app.viewModels
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import com.serjlaren.sloom.databinding.FragmentSplashBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.common.extensions.scaleObjectAnimators
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("MagicNumber")
class SplashFragment : BaseFragment<SplashViewModel>(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels()
    override val viewBinding: FragmentSplashBinding by viewBinding()

    private var scaleUpLogoAnim = listOf(ObjectAnimator())
    private var scaleDownLogoAnim = listOf(ObjectAnimator())
    private var alphaLogoAnim = listOf(ObjectAnimator())
    private var scalePreDownNameAnim = listOf(ObjectAnimator())
    private var alphaNameAnim = listOf(ObjectAnimator())
    private var scaleUpNameAnim = listOf(ObjectAnimator())
    private var scaleDownNameAnim = listOf(ObjectAnimator())

    override fun initViews() {
        super.initViews()

        scaleUpLogoAnim = viewBinding.logoImageView.scaleObjectAnimators(1f, 1.5f, 500)
        scaleDownLogoAnim = viewBinding.logoImageView.scaleObjectAnimators(1.5f, 0.9f, 300)
        alphaLogoAnim = listOf(
            ObjectAnimator.ofFloat(viewBinding.logoImageView, View.ALPHA, 1f, 0f).apply {
                duration = 500
                doOnEnd {
                    viewBinding.logoImageView.visibility = View.GONE
                }
            })
        scalePreDownNameAnim = viewBinding.gameNameTextView.scaleObjectAnimators(1.2f, 1f, 250, 250)
        alphaNameAnim = listOf(
            ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.ALPHA, 0f, 1f).apply {
                duration = 250
                startDelay = 250
                doOnStart {
                    viewBinding.gameNameTextView.visibility = View.VISIBLE
                }
            })
        scaleUpNameAnim = viewBinding.gameNameTextView.scaleObjectAnimators(1f, 1.5f, 500)
        scaleDownNameAnim = viewBinding.gameNameTextView.scaleObjectAnimators(1.5f, 0f, 500)
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            with(viewBinding) {
                bindText(bySerjLarenText, bySerjLarenTextView)
                bindText(gameNameText, gameNameTextView)
                bindCommand(startSplashAnimationCommand) {
                    startLogoAnimations { viewModel.onAnimationEnd() }
                }
            }
        }
    }

    private fun startLogoAnimations(onEnd: () -> Unit) {
        AnimatorSet().apply {
            playTogether(scaleUpLogoAnim)
            doOnEnd {
                AnimatorSet().apply {
                    playTogether(
                        listOf(
                            scaleDownLogoAnim,
                            alphaLogoAnim,
                            alphaNameAnim,
                            scalePreDownNameAnim,
                        ).flatten()
                    )
                    doOnEnd {
                        AnimatorSet().apply {
                            playTogether(scaleUpNameAnim)
                            startDelay = 100
                            doOnEnd {
                                AnimatorSet().apply {
                                    playTogether(scaleDownNameAnim)
                                    doOnEnd {
                                        onEnd.invoke()
                                    }
                                }.start()
                            }
                        }.start()
                    }
                }.start()
            }
        }.start()
    }
}
