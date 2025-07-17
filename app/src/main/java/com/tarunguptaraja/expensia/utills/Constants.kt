package com.tarunguptaraja.expensia.utills

object Constants {

    const val JWT_TOKEN = "JWT_TOKEN"
    const val FIRST_TIME_USER = "FIRST_TIME_USER"
    const val IS_LOGIN = "IS_LOGIN"

    enum class AuthState {
        LOGIN, SIGNUP, VERIFY_OTP, FORGOT_PASSWORD, RESET_PASSWORD
    }
}