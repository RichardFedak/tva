package com.example.myapplication

import android.app.AlertDialog
import android.content.Context

class ConfirmationDialogFactory(
        private val question: String,
        private val positiveButtonText: String,
        private val negativeButtonText: String,
        private val positiveAnswerHandler: () -> Unit
    ) {
    fun createAlertDialog(context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)

        builder.setMessage(question)
            .setPositiveButton(positiveButtonText) { _, _ ->
                positiveAnswerHandler()
            }
            .setNegativeButton(negativeButtonText) { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}
