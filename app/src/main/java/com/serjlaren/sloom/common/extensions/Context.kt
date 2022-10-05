package com.serjlaren.sloom.common.extensions

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

@Suppress("LongParameterList")
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
    positiveActionTitleId?.let {
        builder.setPositiveButton(it) { dialog, _ ->
            dialog.dismiss()
            positiveAction?.invoke()
        }
    }
    negativeActionTitleId?.let {
        builder.setNegativeButton(it) { dialog, _ ->
            dialog.dismiss()
            negativeAction?.invoke()
        }
    }

    builder.setOnCancelListener {
        cancelAction?.invoke()
    }

    builder.create().show()
}