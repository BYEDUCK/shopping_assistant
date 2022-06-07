package com.byeduck.shoppingassistant.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.byeduck.shoppingassistant.databinding.ErrorDialogBinding
import com.byeduck.shoppingassistant.remote.ErrorResponse

class ErrorDialog(layoutInflater: LayoutInflater, context: Context, error: ErrorResponse) {

    private val alertDialog: AlertDialog

    init {
        val dialogBinding = ErrorDialogBinding.inflate(layoutInflater)
        alertDialog = AlertDialog.Builder(context)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()
        dialogBinding.errorStatusText.text = error.status.toString()
        dialogBinding.errorMsgText.text = error.error
        dialogBinding.requestIdText.text = error.requestId
        dialogBinding.errorDismissButton.setOnClickListener { alertDialog.dismiss() }
    }

    fun show(onDismissAction: () -> Unit = {}) {
        alertDialog.show()
        alertDialog.setOnDismissListener { onDismissAction() }
    }
}