package com.serjlaren.sloom.common.extensions

import android.animation.ObjectAnimator
import android.view.View

fun View.scaleObjectAnimators(from: Float, to: Float, animDuration: Long, animStartDelay: Long = 0) = listOf(
    ObjectAnimator.ofFloat(this, View.SCALE_X, from, to).apply {
        duration = animDuration
        startDelay = animStartDelay
    },
    ObjectAnimator.ofFloat(this, View.SCALE_Y, from, to).apply {
        duration = animDuration
        startDelay = animStartDelay
    },
)