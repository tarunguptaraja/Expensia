package com.tarunguptaraja.expensia.extensions

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.databinding.ErrorDialogBinding

var errorDialog: BottomSheetDialog? = null
fun Activity.showErrorDialog(
    message: String = resources.getString(R.string.server_error), shouldLogout: Boolean = false
) {
    try {
        if (!isRunning()) return
        if (errorDialog == null || !errorDialog!!.isShowing) {
            val dialogView = ErrorDialogBinding.inflate(layoutInflater)

            errorDialog = BottomSheetDialog(this, R.style.Theme_Transparent)
            errorDialog?.setContentView(dialogView.root)
            errorDialog?.setCancelable(false)
            errorDialog?.window?.statusBarColor = Color.TRANSPARENT

            errorDialog?.window?.setWindowAnimations(R.style.BottomSheetAnim)

            if (!isRunning()) return
            errorDialog?.show()

            dialogView.tvMsgError.text = message
            if (shouldLogout) {
                logoutAndRemoveData()
            } else {
                logoutAndRemoveData()
            }

            dialogView.btnOkErrorDialog.onOneClick {
                errorDialog?.dismiss()
                errorDialog = null
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}