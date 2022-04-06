package com.serjlaren.sloom.common.mvvm

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<TViewModel : BaseViewModel>(@LayoutRes layoutResId: Int) :
    Fragment(layoutResId) {

    protected abstract val viewModel: TViewModel

    open fun bindViewModel() {

    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }
}