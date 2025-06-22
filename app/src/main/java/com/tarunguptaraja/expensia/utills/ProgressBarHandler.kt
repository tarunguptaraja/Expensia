package com.tarunguptaraja.expensia.utills


import android.app.Activity
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.extensions.runOnUiThreadIfRunning
import java.lang.Exception

class ProgressBarHandler(private val activity: Activity) {
    var handler: Handler? = null
    val hideRunnable = Runnable { hide() }

    init {
        activity.runOnUiThreadIfRunning {
            handler = Handler()
        }
    }

    private lateinit var view: View
    val layout = activity.findViewById<View>(android.R.id.content).rootView as ViewGroup


    fun show(apiName: String = "") {
        activity.runOnUiThreadIfRunning {
            try {
                handler?.let { it.postDelayed(hideRunnable, 30000) }
                activity.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )

                view = activity.layoutInflater.inflate(R.layout.progressbar, null)
                view.z = 9999f
                /*if (BuildConfig.DEBUG) {
                    view.tvApiName.visibility = View.VISIBLE
                    view.tvApiName.text = apiName
                }*/
                layout.addView(view)
                view.visibility = View.VISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun hide() {
        activity.runOnUiThreadIfRunning {
            try {
//                handler?.removeCallbacks(hideRunnable)
                handler?.let { it.removeCallbacks(hideRunnable) }
                activity.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                if (::view.isInitialized)
                    layout.removeView(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}