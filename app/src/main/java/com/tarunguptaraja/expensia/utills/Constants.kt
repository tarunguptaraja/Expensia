package com.tarunguptaraja.expensia.utills

object Constants {

    const val JWT_TOKEN = "JWT_TOKEN"

    enum class AuthState {
        LOGIN, SIGNUP, VERIFY_OTP, FORGOT_PASSWORD, RESET_PASSWORD
    }
}