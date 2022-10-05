package com.serjlaren.sloom.common.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.serjlaren.sloom.R

abstract class BaseBottomSheetDialogFragment<TViewBinding : ViewBinding, TViewModel : BaseViewModel> :
    BottomSheetDialogFragment() {

    protected abstract val viewModel: TViewModel
    protected var viewBinding: TViewBinding? = null
    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TViewBinding

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = bindingInflater.invoke(inflater, container, false)
        return viewBinding!!.root
    }
}
