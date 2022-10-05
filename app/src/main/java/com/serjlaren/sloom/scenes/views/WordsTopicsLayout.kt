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

@Suppress("MagicNumber")
class WordsTopicsLayout : ConstraintLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    private var checkBoxes = mutableListOf<CheckBox>()
    private val viewBinding = LayoutWordsTopicsBinding.inflate(LayoutInflater.from(context), this, true)

    private fun onCheckBoxChecked(index: Int, checked: Boolean) {
        if (!checked)
            return

        when (index) {
            0 -> {
                checkBoxes.filterIndexed { i, _ -> i != 0 }.forEach {
                    it.isChecked = false
                }
            }
            else -> {
                checkBoxes.elementAt(0).isChecked = false
            }
        }
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

        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { _, checked ->
                onCheckBoxChecked(index, checked)
            }
            viewBinding.checkBoxesLayout.addView(checkBox)
        }
    }

    fun setChecked(index: Int, checked: Boolean) {
        checkBoxes.elementAtOrNull(index)?.let { checkBox ->
            checkBox.isChecked = checked
        }
    }

    fun getCheckedIndexes() =
        checkBoxes.mapIndexed { index, checkBox -> if (checkBox.isChecked) index else null }
            .filterNotNull()
}
