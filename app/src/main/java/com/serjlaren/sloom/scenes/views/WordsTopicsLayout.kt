package com.serjlaren.sloom.scenes.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.serjlaren.sloom.R
import com.serjlaren.sloom.databinding.LayoutWordsTopicsBinding

class WordsTopicsLayout : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init()
    }

    private var checkBoxes = mutableListOf<CheckBox>()
    private val viewBinding = LayoutWordsTopicsBinding.inflate(LayoutInflater.from(context), this, true)

    private fun init() {
    }

    fun setCheckBoxes(values: List<String>) {
        checkBoxes.clear()
        viewBinding.checkBoxesLayout.removeAllViews()

        values.forEach { value ->
            checkBoxes.add(CheckBox(context).apply {
                setTextColor(ContextCompat.getColor(context, R.color.teal_200))
                text = value
                layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                    topMargin = 10
                    bottomMargin = 10
                }
            })
        }

        checkBoxes.forEach { checkBox ->
            viewBinding.checkBoxesLayout.addView(checkBox)
        }
    }
}