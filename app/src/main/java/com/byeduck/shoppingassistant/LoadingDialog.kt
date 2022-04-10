package com.byeduck.shoppingassistant

import android.app.Activity
import android.app.AlertDialog
import com.byeduck.shoppingassistant.databinding.LoadingDialogBinding

class LoadingDialog(activity: Activity) {

    private val dialog: AlertDialog

    init {
        val dialogBinding = LoadingDialogBinding.inflate(activity.layoutInflater)
        dialog = AlertDialog.Builder(activity)
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