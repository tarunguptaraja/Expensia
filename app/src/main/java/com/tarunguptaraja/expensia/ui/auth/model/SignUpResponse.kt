package com.tarunguptaraja.expensia.ui.auth.model

import com.tarunguptaraja.expensia.base.BaseModel

data class SignUpResponse(
    val data: SignUpData
) : BaseModel()

data class SignUpData(
    val user: User, val token: String, val refreshToken: String
)