package com.serjlaren.sloom.scenes.splash

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

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            with(viewBinding) {
                bindText(bySerjLarenText, bySerjLarenTextView)
                bindCommand(startSplashAnimation) {
                    viewModel.onAnimationEnd()
                }
            }
        }
    }
}