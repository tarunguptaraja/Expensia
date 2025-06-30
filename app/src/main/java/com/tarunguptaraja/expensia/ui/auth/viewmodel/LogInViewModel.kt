package com.tarunguptaraja.expensia.ui.auth.viewmodel

import com.google.gson.JsonObject
import com.tarunguptaraja.expensia.base.BaseViewModel
import com.tarunguptaraja.expensia.retrofit.execute

class LogInViewModel: BaseViewModel() {

    fun requestOtp(callback: ((JsonObject) -> Unit)? = null){
        val jsonObject = JsonObject().apply {
            addProperty("email", "tarunguptaraja.tgr@gmail.com")
        }
        apiServicesPlatform.requestOtp(jsonObject).execute(activity, true) {
            callback?.invoke(it)
        }
    }
}