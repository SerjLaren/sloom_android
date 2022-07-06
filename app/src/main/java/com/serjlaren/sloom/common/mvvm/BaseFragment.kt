package com.serjlaren.sloom.common.mvvm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.*
import com.serjlaren.sloom.common.extensions.*
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<TViewModel : BaseViewModel>(@LayoutRes layoutResId: Int) : Fragment(layoutResId) {

    protected abstract val viewModel: TViewModel
    protected abstract val viewBinding: ViewBinding

    open fun initViews() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.backButtonPressed()
            }
        })
    }

    open fun bindViewModel() {
        with(viewModel) {
            bindTCommand(navigateToScreenCommand) { screen ->
                when (screen) {
                    //App Screens
                    is Screen.AppScreen.Splash -> findNavController().navigate(R.id.splashFragment, null, navOptions())
                    is Screen.AppScreen.Main -> findNavController().navigate(
                        R.id.mainFragment, null, NavOptions.Builder()
                            .setPopUpTo(findNavController().backQueue.first().destination.id, true)
                            .setLaunchSingleTop(true)
                            .setEnterAnim(R.anim.fragment_enter)
                            .setExitAnim(R.anim.fragment_exit)
                            .setPopEnterAnim(R.anim.fragment_exit)
                            .setPopExitAnim(R.anim.fragment_enter)
                            .build()
                    )
                    is Screen.AppScreen.GameSettings -> findNavController().navigate(R.id.gameSettingsFragment, null, navOptions())
                    is Screen.AppScreen.Game -> findNavController().navigate(R.id.gameFragment, null, navOptions())
                    is Screen.AppScreen.About -> findNavController().navigate(R.id.aboutFragment, null, navOptions())
                    is Screen.AppScreen.Rules -> {  } //TODO

                    //External screens
                    is Screen.ExternalScreen.SourceCode -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(screen.url)))
                    is Screen.ExternalScreen.AboutMe -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(screen.url)))
                }
            }
            bindCommand(navigateBackCommand) {
                if (findNavController().popBackStack().not()) {
                    requireActivity().finish()
                }
            }
            bindTCommand(showToastCommand) { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
            bindTCommand(showToastFromResourcesCommand) { messageId ->
                Toast.makeText(requireContext(), requireContext().getString(messageId), Toast.LENGTH_SHORT).show()
            }
            bindTCommand(showAlertCommand) { model ->
                requireContext().showAlertDialog(
                    title = model.title,
                    message = model.message,
                    positiveActionTitleId = model.positiveActionTitleId,
                    negativeActionTitleId = model.negativeActionTitleId,
                    positiveAction = model.positiveAction,
                    negativeAction = model.negativeAction,
                    cancelAction = model.cancelAction,
                )
            }
        }
    }

    private fun navOptions(): NavOptions =
        NavOptions.Builder()
            .setEnterAnim(R.anim.fragment_enter)
            .setExitAnim(R.anim.fragment_exit)
            .setPopEnterAnim(R.anim.fragment_enter)
            .setPopExitAnim(R.anim.fragment_exit)
            .build()

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