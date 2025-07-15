package com.tarunguptaraja.expensia.retrofit

import com.google.gson.JsonObject
import com.tarunguptaraja.expensia.base.BaseModel
import com.tarunguptaraja.expensia.ui.auth.model.LogInResponse
import com.tarunguptaraja.expensia.ui.auth.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterfacePlatform {

    @POST("auth/request-otp")
    fun requestOtp(@Body jsonObject: JsonObject): Call<BaseModel>

    @POST("auth/signup")
    fun signUp(@Body jsonObject: JsonObject): Call<SignUpResponse>

    @POST("auth/login")
    fun logIn(@Body jsonObject: JsonObject): Call<LogInResponse>

    @POST("auth/forgot-password")
    fun forgotPassword(@Body jsonObject: JsonObject): Call<BaseModel>

    @POST("auth/reset-password")
    fun resetPassword(@Body jsonObject: JsonObject): Call<BaseModel>
}