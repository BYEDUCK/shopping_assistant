package com.byeduck.shoppingassistant.products

import android.app.Activity
import android.app.AlertDialog
import com.byeduck.shoppingassistant.databinding.ErrorDialogBinding
import com.byeduck.shoppingassistant.products.remote.ErrorResponse

class ErrorDialog(activity: Activity, error: ErrorResponse) {

    private val alertDialog: AlertDialog

    init {
        val dialogBinding = ErrorDialogBinding.inflate(activity.layoutInflater)
        alertDialog = AlertDialog.Builder(activity)
            .setView(dialogBinding.root)
            .setCancelable(true)
            .create()
        dialogBinding.errorStatusText.text = error.status.toString()
        dialogBinding.errorMsgText.text = error.error
        dialogBinding.requestIdText.text = error.requestId
        dialogBinding.errorDismissButton.setOnClickListener { alertDialog.dismiss() }
    }

    fun show(onDismissAction: () -> Unit) {
        alertDialog.show()
        alertDialog.setOnDismissListener { onDismissAction() }
    }
}