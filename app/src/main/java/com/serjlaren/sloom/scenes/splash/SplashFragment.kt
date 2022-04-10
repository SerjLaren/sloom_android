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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels()
    override val viewBinding: FragmentSplashBinding by viewBinding()

    private var scaleUpLogoX = ObjectAnimator()
    private var scaleUpLogoY = ObjectAnimator()
    private var scaleDownLogoX = ObjectAnimator()
    private var scaleDownLogoY = ObjectAnimator()
    private var scaleAlphaLogo = ObjectAnimator()

    private var scalePreDownNameX = ObjectAnimator()
    private var scalePreDownNameY = ObjectAnimator()
    private var scaleAlphaName = ObjectAnimator()
    private var scaleUpNameX = ObjectAnimator()
    private var scaleUpNameY = ObjectAnimator()
    private var scaleDownNameX = ObjectAnimator()
    private var scaleDownNameY = ObjectAnimator()

    override fun initViews() {
        super.initViews()

        scaleUpLogoX = ObjectAnimator.ofFloat(viewBinding.logoImageView, View.SCALE_X, 1f, 1.5f)
            .apply { duration = 500 }
        scaleUpLogoY = ObjectAnimator.ofFloat(viewBinding.logoImageView, View.SCALE_Y, 1f, 1.5f)
            .apply { duration = 500 }
        scaleDownLogoX = ObjectAnimator.ofFloat(viewBinding.logoImageView, View.SCALE_X, 1.5f, 0.9f)
            .apply { duration = 300 }
        scaleDownLogoY = ObjectAnimator.ofFloat(viewBinding.logoImageView, View.SCALE_Y, 1.5f, 0.9f)
            .apply { duration = 300 }
        scaleAlphaLogo =
            ObjectAnimator.ofFloat(viewBinding.logoImageView, View.ALPHA, 1f, 0f).apply {
                duration = 500
                doOnEnd {
                    viewBinding.logoImageView.visibility = View.GONE
                }
            }
        scalePreDownNameX =
            ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.SCALE_X, 1.1f, 1f).apply {
                duration = 250
                startDelay = 250
            }
        scalePreDownNameY =
            ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.SCALE_Y, 1.1f, 1f).apply {
                duration = 250
                startDelay = 250
            }
        scaleAlphaName =
            ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.ALPHA, 0f, 1f).apply {
                duration = 250
                startDelay = 250
                doOnStart {
                    viewBinding.gameNameTextView.visibility = View.VISIBLE
                }
            }
        scaleUpNameX = ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.SCALE_X, 1f, 1.5f)
            .apply { duration = 500 }
        scaleUpNameY = ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.SCALE_Y, 1f, 1.5f)
            .apply { duration = 500 }
        scaleDownNameX =
            ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.SCALE_X, 1.5f, 0f)
                .apply { duration = 500 }
        scaleDownNameY =
            ObjectAnimator.ofFloat(viewBinding.gameNameTextView, View.SCALE_Y, 1.5f, 0f)
                .apply { duration = 500 }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            with(viewBinding) {
                bindText(bySerjLarenText, bySerjLarenTextView)
                bindText(gameNameText, gameNameTextView)
                bindCommand(startSplashAnimation) {
                    AnimatorSet().apply {
                        playTogether(scaleUpLogoX, scaleUpLogoY)
                        doOnEnd {
                            AnimatorSet().apply {
                                playTogether(
                                    scaleDownLogoX,
                                    scaleDownLogoY,
                                    scaleAlphaLogo,
                                    scaleAlphaName,
                                    scalePreDownNameX,
                                    scalePreDownNameY
                                )
                                doOnEnd {
                                    AnimatorSet().apply {
                                        playTogether(scaleUpNameX, scaleUpNameY)
                                        startDelay = 100
                                        doOnEnd {
                                            AnimatorSet().apply {
                                                playTogether(scaleDownNameX, scaleDownNameY)
                                                doOnEnd {
                                                    viewModel.onAnimationEnd()
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
        }
    }
}