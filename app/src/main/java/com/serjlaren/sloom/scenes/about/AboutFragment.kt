package com.serjlaren.sloom.scenes.about

import androidx.fragment.app.viewModels
import com.serjlaren.sloom.R
import com.serjlaren.sloom.common.mvvm.BaseFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.serjlaren.sloom.databinding.FragmentAboutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : BaseFragment<AboutViewModel>(R.layout.fragment_about) {
    override val viewModel: AboutViewModel by viewModels()
    override val viewBinding: FragmentAboutBinding by viewBinding()

    override fun initViews() {
        super.initViews()
        with(viewBinding) {
            sourceCodeClickableLayout.setOnClickListener {
                viewModel.sourceCodeButtonClicked()
            }
            aboutMeClickableLayout.setOnClickListener {
                viewModel.aboutMeButtonClicked()
            }
            feedbackClickableLayout.setOnClickListener {
                viewModel.feedbackButtonClicked()
            }
        }
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewBinding) {
            with(viewModel) {
                bindText(aboutText, aboutTextView)
                bindText(sourceCodeButtonText, sourceCodeTextView)
                bindText(aboutMeButtonText, aboutMeTextView)
                bindText(feedbackButtonText, feedbackTextView)
            }
        }
    }
}
