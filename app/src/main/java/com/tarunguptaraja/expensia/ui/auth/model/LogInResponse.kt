package com.tarunguptaraja.expensia.ui.auth.model

import com.tarunguptaraja.expensia.base.BaseModel

data class LogInResponse(val data: LoginData) : BaseModel()

data class ResetData(
    val resetPassword: Boolean, val userId: String, val email: String
)

data class LoginData(
    val resetPassword: Boolean, val user: User, val token: String, val refreshToken: String
)