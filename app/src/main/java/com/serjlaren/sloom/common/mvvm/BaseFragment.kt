package com.serjlaren.sloom.common.mvvm

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.*
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<TViewModel : BaseViewModel>(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    protected abstract val viewModel: TViewModel
    protected abstract val viewBinding: ViewBinding

    open fun initViews() {

    }

    open fun bindViewModel() {
        with(viewModel) {
            bindTCommand(navigateToScreen) { appScreen ->
                when (appScreen) {
                    is AppScreen.Splash -> findNavController().navigate(R.id.splashFragment)
                    is AppScreen.Main -> findNavController().navigate(
                        R.id.mainFragment, null, NavOptions.Builder()
                            .setPopUpTo(findNavController().backQueue.first().destination.id, true)
                            .setLaunchSingleTop(true)
                            .build()
                    )
                    is AppScreen.GameSettings -> findNavController().navigate(R.id.gameSettingsFragment)
                    is AppScreen.Game -> findNavController().navigate(R.id.gameFragment)
                    is AppScreen.About -> TODO()
                    is AppScreen.Rules -> TODO()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        bindViewModel()
        viewModel.init()
    }

    override fun onStart() {
        super.onStart()
        viewModel.start()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    protected fun bindText(sharedFlow: TextFlow, textView: TextView) = sharedFlow.onEach { text ->
        textView.text = text
    }.launchWhenStarted(viewLifecycleOwner, lifecycleScope)

    protected fun bindVisible(sharedFlow: VisibleFlow, view: View, asInvisible: Boolean = false) = sharedFlow.onEach { visible ->
        view.visibility = if (visible) View.VISIBLE else if (asInvisible) View.INVISIBLE else View.GONE
    }.launchWhenStarted(viewLifecycleOwner, lifecycleScope)

    protected fun bindEnabled(sharedFlow: EnabledFlow, view: View) = sharedFlow.onEach { enabled ->
        view.isEnabled = enabled
    }.launchWhenStarted(viewLifecycleOwner, lifecycleScope)

    protected fun <T> bindData(sharedFlow: DataFlow<T>, block: (T) -> Unit) = sharedFlow.onEach { data ->
        block(data)
    }.launchWhenStarted(viewLifecycleOwner, lifecycleScope)

    protected fun bindCommand(sharedFlow: CommandFlow, block: () -> Unit) = sharedFlow.onEach {
        block()
    }.launchWhenStarted(viewLifecycleOwner, lifecycleScope)

    protected fun <T> bindTCommand(sharedFlow: TCommandFlow<T>, block: (T) -> Unit) = sharedFlow.onEach { data ->
        block(data)
    }.launchWhenStarted(viewLifecycleOwner, lifecycleScope)
}