package com.tarunguptaraja.expensia.ui.auth.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.tarunguptaraja.expensia.Expensia
import com.tarunguptaraja.expensia.MainActivity
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        handler.postDelayed({
            val hasSeenOnboarding = Expensia.sharedPreferences.getBoolean("onboarding_seen", false)
            if (hasSeenOnboarding) {
                // User already saw onboarding
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // First launch → show onboarding
                startActivity(Intent(this, OnboardingActivity::class.java))
            }
            finish()
        }, 1500)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}