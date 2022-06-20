package com.serjlaren.sloom.common.extensions

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showAlertDialog(
    title: String?,
    message: String?,
    positiveActionTitleId: Int? = null,
    negativeActionTitleId: Int? = null,
    positiveAction: (() -> Unit)? = null,
    negativeAction: (() -> Unit)? = null,
    cancelAction: (() -> Unit)? = null,
) {
    val builder = MaterialAlertDialogBuilder(this)

    title?.let { builder.setTitle(title) }
    message?.let { builder.setMessage(message) }

    if (positiveActionTitleId != null) {
        builder.setPositiveButton(positiveActionTitleId) { dialog, _ ->
            dialog.dismiss()
            positiveAction?.invoke()
        }
    }

    if (negativeActionTitleId != null) {
        builder.setNegativeButton(negativeActionTitleId) { dialog, _ ->
            dialog.dismiss()
            negativeAction?.invoke()
        }
    }

    builder.setOnCancelListener {
        cancelAction?.invoke()
    }

    builder.create().show()
}