package com.tarunguptaraja.expensia.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.tarunguptaraja.expensia.Expensia
import com.tarunguptaraja.expensia.R
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
        window.statusBarColor = ContextCompat.getColor(this, R.color.light_100)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun getScreenOrientation(): Int {
        val rotation = windowManager.defaultDisplay.rotation
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width: Int = dm.widthPixels
        val height: Int = dm.heightPixels
        // if the device's natural orientation is portrait:
        val orientation: Int =
            if ((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && height > width || (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) && width > height) {
                when (rotation) {
                    Surface.ROTATION_0 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    Surface.ROTATION_90 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    Surface.ROTATION_270 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    else -> {
                        Log.e(
                            "TAG", "Unknown screen orientation. Defaulting to " + "portrait."
                        )
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    }
                }
            } else {
                when (rotation) {
                    Surface.ROTATION_0 -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    Surface.ROTATION_90 -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    Surface.ROTATION_180 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
                    Surface.ROTATION_270 -> ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                    else -> {
                        Log.e(
                            "TAG", "Unknown screen orientation. Defaulting to " + "landscape."
                        )
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }
                }
            }
        return orientation
    }
}