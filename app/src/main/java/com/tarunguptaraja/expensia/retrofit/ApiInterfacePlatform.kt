package com.tarunguptaraja.expensia.retrofit

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterfacePlatform {

    @POST("auth/request-otp")
    fun requestOtp(@Body jsonObject: JsonObject): Call<JsonObject>
}