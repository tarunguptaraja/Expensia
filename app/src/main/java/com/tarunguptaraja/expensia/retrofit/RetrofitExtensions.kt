package com.tarunguptaraja.expensia.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import com.tarunguptaraja.expensia.BuildConfig
import com.tarunguptaraja.expensia.Expensia
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.extensions.calculateDifference
import com.tarunguptaraja.expensia.extensions.runOnMain
import com.tarunguptaraja.expensia.extensions.showErrorDialog
import com.tarunguptaraja.expensia.utills.ProgressBarHandler
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.Call
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException
import kotlin.concurrent.thread

val HttpUrl.endPoint: String
    get() {
        val apiUrl = toString().split("/")

        var endPoint = ""
        if (!apiUrl.get(apiUrl.size - 1).isNullOrEmpty()) endPoint =
            apiUrl.get(apiUrl.size - 2) + "/" + apiUrl.get(apiUrl.size - 1)
        else endPoint = apiUrl.get(apiUrl.size - 3) + "/" + apiUrl.get(apiUrl.size - 2)
        return endPoint.split("?")[0]
    }

fun Interceptor.Chain.proceedByCheckingCache(request: Request): Response {
    val endPoint = request.url.endPoint
    val cashedRes = getCashedResponse(endPoint)
    return if (cashedRes == null) {
        val response = proceed(request)
        if (response.code == 200) {
            if (endPointsWithTimeMap.containsKey(endPoint)) {
                val res = response.body?.string()
                endPointsWithLastTimeCall[endPoint] = System.currentTimeMillis() to res

                return Response.Builder().code(200).request(request).protocol(Protocol.HTTP_2)
                    .message("Saved to cache")
                    .body(res!!.toResponseBody("application/json".toMediaTypeOrNull()))
                    .addHeader("content-type", "application/json").build()
            }
        }
        response
    } else {
        Log.d("OkHttp", "<-- 200 OK ${request.url} (From cache)")
        Response.Builder().code(200).request(request).protocol(Protocol.HTTP_2)
            .message("From cache")
            .body(cashedRes.toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json").build()
    }
}


val apiNetworkFailedList = arrayListOf<Pair<Call<Any>?, (Any) -> Unit>>()
val apisInProgress = mutableListOf<String>()
fun <T : Any> Call<T>.execute(
    activity: BaseActivity? = null,
    showLoading: Boolean = true,
    retryStrategy: RetryStrategy<T>? = null,
    force: Boolean = false,
    checkIsApisInProgress: Boolean = true,
    callback: (T) -> Unit
) {
    val requestInfo = request()
    val endPoint = requestInfo.url.endPoint

    if (showLoading) {
        Log.d("loadingApi", endPoint)
    }

    if (apisInProgress.contains(endPoint) && checkIsApisInProgress) {
        return
    }

    if (force) {
        if (requestInfo.url.isCached) {

        }
        endPointsWithLastTimeCall.remove(endPoint)
    }

    val progressBarHandler: ProgressBarHandler? =
        activity?.let { if (showLoading) ProgressBarHandler(activity) else null }
    thread {
        runOnMain {
            progressBarHandler?.show(endPoint)
        }
        if (activity != null) {
            (activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.let { networkInfo ->
                if (networkInfo == null || !networkInfo.isConnected) {
                    Thread.sleep(500)
                    progressBarHandler?.hide()
                    Log.d("getScreenOrientation", "${activity.getScreenOrientation()}")
                    apiNetworkFailedList.add(Pair(this as Call<Any>, callback as (Any) -> Unit))
                    Toast.makeText(
                        Expensia.appContext,
                        Expensia.appContext!!.getString(R.string.check_your_mobile_data_or_wifi_connection),
                        Toast.LENGTH_LONG
                    ).show()/*activity.networkErrorDialog(refresh = {
                        Handler().postDelayed({
                            apiNetworkFailedList.forEach {
                                try {
                                    it.first?.execute(
                                        activity,
                                        showLoading,
                                        retryStrategy as RetryStrategy<Any>?,
                                        force,
                                        checkIsApisInProgress,
                                        it.second
                                    )
                                } catch (ex: Exception) {

                                }
                            }
                            apiNetworkFailedList.clear()
                            progressBarHandler?.hide()
                        }, 200)
                    })*/

                    return@thread
                }
            }
        }

        try {
            apisInProgress.add(endPoint)
            val response = execute()

            val code = response.code()

            val headers = response.headers()

            val result: T?

            if (code == 400 || code == 404 || code == 500) {
                val url = requestInfo.url.toString()
                Toast.makeText(
                    Expensia.appContext, "Error in ${
                    url.split("/").let {
                        val l = it.last()
                        if (l.isEmpty()) {
                            it[it.size - 2]
                        } else {
                            l
                        }
                    }
                }", Toast.LENGTH_LONG).show()
            }

            when (code) {
                400 -> {
                    runOnMain {
                        progressBarHandler?.hide()
                        Toast.makeText(Expensia.appContext, "400", Toast.LENGTH_LONG).show()
//                        activity?.showMaintenanceAndServerErrorBottomSheet(true)
                    }
                    result = null
                }

                500 -> {
                    runOnMain {
                        progressBarHandler?.hide()
                        Toast.makeText(Expensia.appContext, "400", Toast.LENGTH_LONG).show()
//                        activity?.showMaintenanceAndServerErrorBottomSheet(true)
                    }
                    result = null
                }

                401, 403 -> {
                    runOnMain {
                        progressBarHandler?.hide()
                        response.errorBody()?.string()?.let { error ->
                            val jsonObj = JSONObject(error)
                            val message = jsonObj.optString("message")
                            activity?.showErrorDialog(message, true)
                        }
                    }
                    result = null
                }

                402, 404 -> {
                    runOnMain {
                        progressBarHandler?.hide()
                        response.errorBody()?.string()?.let { error ->
                            val jsonObj = JSONObject(error)
                            val message = jsonObj.optString("message")
                            activity?.showErrorDialog(message, false)
                        }
                    }
                    result = null
                }

                in 200..299 -> {
                    val body = response.body()
                    result = if (body == null) {
                        runOnMain {
                            progressBarHandler?.hide()
                            Toast.makeText(Expensia.appContext, "Body is null", Toast.LENGTH_LONG)
                                .show()
//                            activity?.showServerErrorDialog(requestInfo, "Body is null")
                        }
                        null
                    } else {
                        body
                    }
                }

                else -> {
                    runOnMain {
                        progressBarHandler?.hide()
//                        activity?.showServerErrorDialog(requestInfo, "Error code: $code")
                        Toast.makeText(Expensia.appContext, "Error code: $code", Toast.LENGTH_LONG)
                            .show()
                    }
                    result = null
                }
            }

            if (retryStrategy != null) {
                if (retryStrategy.times > 0 && retryStrategy.predicate(response.body(), code)) {
                    retryStrategy.times--
                    this.clone().execute(
                        activity, showLoading, retryStrategy, force, checkIsApisInProgress, callback
                    )
                }
            }

            runOnMain {
                headers.getDate("Date")?.time?.let { calculateDifference(it) }

                progressBarHandler?.hide()
                result?.let {
                    try {
                        callback(it)
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        if (BuildConfig.DEBUG) {
                            val url = requestInfo.url.toString()
                            Toast.makeText(
                                Expensia.appContext, "Error in ${
                                url.split("/").let {
                                    val l = it.last()
                                    if (l.isEmpty()) {
                                        it[it.size - 2]
                                    } else {
                                        l
                                    }
                                }
                            }", Toast.LENGTH_LONG).show()

                            /*AppManager.sendCrash(url, ex, AppManager.CrashType.API, requestInfo.payload(), result, activity, snack)*/
                        }
                    }
                }
            }
            apisInProgress.remove(endPoint)
        } catch (e: SocketTimeoutException) {
            apisInProgress.remove(endPoint)
            Log.d("Exception_SocketTimeoutException", "${e.message}")
        } catch (e: SSLHandshakeException) {
            apisInProgress.remove(endPoint)
            Log.d("Exception_SSLHandshakeException", "${e.message}")
        } catch (e: SSLException) {
            apisInProgress.remove(endPoint)
            Log.d("Exception_SSLException", "${e.message}")
        } catch (e: UnknownHostException) {
            apisInProgress.remove(endPoint)
            Log.d("Exception_UnknownHostException", "${e.message}")
        } catch (e: ConnectException) {
            apisInProgress.remove(endPoint)
            Log.d("Exception_ConnectException", "${e.message}")
        } catch (e: IllegalStateException) {
            apisInProgress.remove(endPoint)
        } catch (e: Exception) {
            apisInProgress.remove(endPoint)
            Log.d("Exception_Exception", "${e.message}")
            e.printStackTrace()
            runOnMain {
                progressBarHandler?.hide()
                Toast.makeText(Expensia.appContext, e.message, Toast.LENGTH_LONG).show()
//                activity?.showServerErrorDialog(requestInfo, e.message)
            }
        }
        runOnMain {
            progressBarHandler?.hide()
        }
    }
}