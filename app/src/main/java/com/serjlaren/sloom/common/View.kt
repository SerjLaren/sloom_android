package com.serjlaren.sloom.common

import android.animation.ObjectAnimator
import android.view.View

fun View.scaleObjectAnimators(from: Float, to: Float, animDuration: Long) = listOf(
    ObjectAnimator.ofFloat(this, View.SCALE_X, from, to).apply { duration = animDuration },
    ObjectAnimator.ofFloat(this, View.SCALE_Y, from, to).apply { duration = animDuration },
)

fun View.alphaObjectAnimator(from: Float, to: Float, animDuration: Long) = listOf(
    ObjectAnimator.ofFloat(this, View.ALPHA, from, to).apply { duration = animDuration }
)