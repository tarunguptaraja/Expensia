package com.tarunguptaraja.expensia.extensions

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.tarunguptaraja.expensia.utills.OnOneClickListener
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject


val gson = Gson()
val gsonPretty = GsonBuilder().setPrettyPrinting().create()

fun Any.toJson() = gson.toJson(this)
fun Any.toPrettyJson() = gsonPretty.toJson(this)

fun Any.toJsonTree() = gson.toJsonTree(this)

fun Bundle.toJson(): JSONObject {
    val json = json()
    val keys = keySet()
    for (key in keys) {
        try {
            json.put(key, get(key))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
    return json
}

fun String.toJsonTree(): JsonElement {
    return JsonParser.parseString(this)
}

fun json(vararg entries: Pair<String, Any>): JSONObject {
    val jsonObject = JSONObject()
    entries.forEach {
        jsonObject.put(it.first, it.second)
    }
    return jsonObject
}

class GenericParamInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
//            .header("apkSource", if (isFromPlayStore()) "PLAYSTORE" else "WEBSITE") // APK source
//            .header("VersionName", BuildConfig.VERSION_NAME) // Version name
            .build()
        return chain.proceed(modifiedRequest)
    }
}

fun Activity?.isRunning(): Boolean = if (this == null) false else !(isDestroyed || isFinishing)

fun Any?.isActivityRunning(): Boolean = if (this == null) false else when {
    this is Activity -> isRunning()
    this is Fragment -> if (activity == null) false else activity!!.isRunning()
    else -> false
}

private val mainHandler = Handler(Looper.getMainLooper())
val mainHandler2 = Handler(Looper.getMainLooper())
private val mainHandler3 = Handler(Looper.getMainLooper())

fun runOnMain(code: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        code()
    } else {
        mainHandler.post {
            try {
                code()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}

fun Activity.runOnUiThreadIfRunning(code: () -> Unit) {
    if (isRunning()) {
        Handler(Looper.getMainLooper()).post {
            code()
        }
    }
}

var timeDiffWithServer = 0L
fun calculateDifference(serverTime: Long) {
    timeDiffWithServer = System.currentTimeMillis() - serverTime
}

var globalButtonClickTIme: Long = 0
fun View.onOneClick(time: Long = 1000, callback: () -> Unit) {
    setOnClickListener(object : OnOneClickListener(time) {
        override fun onOneClick(v: View) {
            callback()
        }
    })
}

fun Activity.logoutAndRemoveData() {
    putBoolean("IS_USER_LOGIN", false)/*    unSubscribeFromTopic(Constants.TopicName.ALL.name)
        unSubscribeFromTopic(Constants.TopicName.NEWUSER.name)
        unSubscribeFromTopic(Constants.TopicName.PAIDUSER.name)

        clearUserSavedData()
        (this as BaseActivity).initRemoteConfig()
        currentActivity?.profileViewModel?.clearProfileData()
        try {
            currentActivity?.walletVM?._playWalletDetailsResponse?.let {
                it.value = null
            }
            currentActivity?.jaxViewModel?.let {
                it._jaxClubConfig.value = null
                it._jaxRoundLL.value = null
            }
            currentActivity?.cashGamesViewModel?.waitingCashGamesPlans?.let {
                it.value = mutableListOf()
            }
            currentActivity?.walletVM?._bonusBarDetails?.let {
                it.value = null
            }
        } catch (e: Exception) {
            Log.e("/@/", e.localizedMessage)
        }

        activePlanIds.clear()

        val tourneysViewModel: TournysViewModel by store()
        tourneysViewModel.getActiveTournaments()
        userRegistered = false*/
}
