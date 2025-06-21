package com.tarunguptaraja.expensia.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.tarunguptaraja.expensia.Expensia
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


var currentActivity: BaseActivity? = null

val runningActivities = mutableSetOf<String>()

abstract class BaseActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein(Expensia.appContext!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        currentActivity = this
        runningActivities.add(javaClass.simpleName)
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}