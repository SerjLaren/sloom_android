package com.serjlaren.sloom.scenes.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.serjlaren.sloom.databinding.LayoutGameSettingBinding

class GameSettingLayout : ConstraintLayout {
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

    private var rangeStart = 0
    private var rangeEnd = 1
    private val viewBinding = LayoutGameSettingBinding.inflate(LayoutInflater.from(context), this, true)

    private fun init() {
        viewBinding.rangeSeekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setTitleValue(getRangeValue().toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    var titleTextView = viewBinding.titleTextView

    fun setTitleValue(text: String) {
        viewBinding.titleValueTextView.text = text
    }

    fun setRangeStartValue(value: Int) {
        viewBinding.rangeStartTextView.text = value.toString()
        rangeStart = value
    }

    fun setRangeEndValue(value: Int) {
        viewBinding.rangeEndTextView.text = value.toString()
        rangeEnd = value
    }

    fun applyRange() {
        viewBinding.rangeSeekBar.max = rangeEnd - rangeStart
    }

    fun setRangeValue(value: Int) {
        viewBinding.rangeSeekBar.progress = value - rangeStart
        setTitleValue(value.toString())
    }

    fun getRangeValue(): Int {
        return viewBinding.rangeSeekBar.progress + rangeStart
    }
}