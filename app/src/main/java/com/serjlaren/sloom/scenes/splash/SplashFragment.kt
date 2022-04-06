package com.serjlaren.sloom.scenes.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import com.serjlaren.sloom.databinding.FragmentSplashBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashViewModel>(R.layout.fragment_splash) {

    override val viewModel: SplashViewModel by viewModels()
    private val viewBinding: FragmentSplashBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
        viewModel.init()
    }

    private fun initViews() {

    }

    override fun bindViewModel() {
        super.bindViewModel()

    }
}