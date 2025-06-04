package com.tarunguptaraja.expensia.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


var currentActivity: BaseActivity? = null

val runningActivities = mutableSetOf<String>()

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        currentActivity = this
        runningActivities.add(javaClass.simpleName)
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}