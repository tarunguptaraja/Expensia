package com.tarunguptaraja.expensia

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.androidisland.vita.startVita
import com.tarunguptaraja.expensia.retrofit.ApiInterfacePlatform
import com.tarunguptaraja.expensia.retrofit.WebServicesPlatform
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton


class Expensia : Application(), LifecycleObserver, KodeinAware {

    fun isAppOnForeground(context: Context, appPackageName: String): Boolean {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        for (appProcess in appProcesses) {
            if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == appPackageName) {
                return true
            }
        }
        return false
    }

    override fun onCreate() {
        super.onCreate()
        startVita()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        instance = this
        appContext = applicationContext
    }

    override val kodein: Kodein = Kodein.lazy {
        bind() from singleton { WebServicesPlatform.retrofit.create(ApiInterfacePlatform::class.java) }
        bind() from singleton {
            this@Expensia.applicationContext!!.getSharedPreferences(
                "expensia", MODE_PRIVATE
            )
        }
    }

    companion object {
        @get:Synchronized
        var instance: Expensia? = null
            private set
        var appContext: Context? = null
            private set


        val sharedPreferences: SharedPreferences by lazy {
            appContext!!.getSharedPreferences(
                "expensia", MODE_PRIVATE
            )
        }

        val sharedPreferencesPermanent: SharedPreferences by lazy {
            appContext!!.getSharedPreferences(
                "expensiaPermanent", MODE_PRIVATE
            )
        }
    }
}