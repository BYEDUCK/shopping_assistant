package com.byeduck.shoppingassistant.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.byeduck.shoppingassistant.databinding.LoadingDialogBinding

class LoadingDialog(layoutInflater: LayoutInflater, context: Context) {

    private val dialog: AlertDialog

    init {
        val dialogBinding = LoadingDialogBinding.inflate(layoutInflater)
        dialog = AlertDialog.Builder(context)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()
    }

    fun startLoading() {
        dialog.show()
    }

    fun stopLoading() {
        dialog.hide()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}