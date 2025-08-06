package com.tarunguptaraja.expensia.ui.auth.viewmodel

import com.google.gson.JsonObject
import com.tarunguptaraja.expensia.base.BaseModel
import com.tarunguptaraja.expensia.base.BaseViewModel
import com.tarunguptaraja.expensia.retrofit.execute
import com.tarunguptaraja.expensia.ui.auth.model.LogInResponse
import com.tarunguptaraja.expensia.ui.auth.model.SignUpResponse

class LogInViewModel : BaseViewModel() {

    fun requestOtp(email: String, callback: ((BaseModel) -> Unit)? = null) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
        }
        apiServicesPlatform.requestOtp(jsonObject).execute(activity, true, null, true) {
            callback?.invoke(it)
        }
    }

    fun signUp(
        name: String,
        email: String,
        password: String,
        otp: String,
        callback: ((it: SignUpResponse) -> Unit)? = null
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("name", name)
            addProperty("email", email)
            addProperty("password", password)
            addProperty("termsAccepted", true)
            addProperty("otp", otp)
        }
        apiServicesPlatform.signUp(jsonObject).execute(activity, true, null, true) {
            callback?.invoke(it)
        }
    }

    fun logIn(email: String, password: String, callback: ((LogInResponse) -> Unit)? = null) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
            addProperty("password", password)
        }
        apiServicesPlatform.logIn(jsonObject).execute(activity, true, null, false) {
            callback?.invoke(it)
        }
    }

    fun forgotPassword(email: String, callback: ((BaseModel) -> Unit)? = null) {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
        }
        apiServicesPlatform.forgotPassword(jsonObject).execute(activity, true, null, false) {
            callback?.invoke(it)
        }
    }

    fun resetPassword(
        userId: String = "", password: String, callback: ((BaseModel) -> Unit)? = null
    ) {
        val jsonObject = JsonObject().apply {
            addProperty("userId", userId)
            addProperty("newPassword", password)
            addProperty("confirmPassword", password)
        }
        apiServicesPlatform.resetPassword(jsonObject).execute(activity, true, null, false) {
            callback?.invoke(it)
        }
    }
}