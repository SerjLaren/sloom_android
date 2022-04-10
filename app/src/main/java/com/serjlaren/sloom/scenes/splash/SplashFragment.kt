package com.serjlaren.sloom.scenes.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
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

    var scaleUpLogoX = ObjectAnimator()
    var scaleUpLogoY = ObjectAnimator()

    override fun initViews() {
        super.initViews()

        scaleUpLogoX = ObjectAnimator.ofFloat(viewBinding.logoImageView, View.SCALE_X, 1f, 0f)
        scaleUpLogoY = ObjectAnimator.ofFloat(viewBinding.logoImageView, View.SCALE_Y, 1f, 0f)
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            with(viewBinding) {
                bindText(bySerjLarenText, bySerjLarenTextView)
                bindCommand(startSplashAnimation) {
                    AnimatorSet().apply {
                        playTogether(scaleUpLogoX, scaleUpLogoY)
                        duration = 1000
                        doOnEnd { viewModel.onAnimationEnd() }
                    }.start()
                }
            }
        }
    }
}