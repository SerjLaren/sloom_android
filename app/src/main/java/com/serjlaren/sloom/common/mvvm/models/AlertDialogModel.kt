package com.serjlaren.sloom.common.mvvm.models

import com.serjlaren.sloom.R

data class AlertDialogModel(
    val title: String? = null,
    val message: String? = null,
    val positiveActionTitleId: Int = R.string.scr_any_ok,
    val negativeActionTitleId: Int? = null,
    val positiveAction: (() -> Unit)? = null,
    val negativeAction: (() -> Unit)? = null,
    val cancelAction: (() -> Unit)? = null,
)
