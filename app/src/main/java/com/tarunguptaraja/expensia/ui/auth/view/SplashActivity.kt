package com.tarunguptaraja.expensia.ui.auth.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import com.tarunguptaraja.expensia.MainActivity
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivitySplashBinding
import com.tarunguptaraja.expensia.extensions.putPermanentBoolean
import com.tarunguptaraja.expensia.extensions.retrievePermanentBoolean
import com.tarunguptaraja.expensia.extensions.retrieveString
import com.tarunguptaraja.expensia.utills.Constants

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())
    val firstTimeUser by lazy { retrievePermanentBoolean(Constants.FIRST_TIME_USER) }
    val alreadyLoggedIn by lazy { retrieveString(Constants.JWT_TOKEN).isNotEmpty() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater, null, false)
        enableEdgeToEdge()
        setContentView(binding.root)
        handler.postDelayed({
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            if (/*alreadyLoggedIn ||*/ false) {
                startActivity(Intent(this, MainActivity::class.java))
            } else if (firstTimeUser || true) {
                startActivity(Intent(this, OnboardingActivity::class.java))
                putPermanentBoolean(Constants.FIRST_TIME_USER, false)
            } else {
                startActivity(Intent(this, AuthenticationActivity::class.java))
            }
            finish()
        }, 1500)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}