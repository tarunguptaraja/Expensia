package com.tarunguptaraja.expensia.ui.auth.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        enableEdgeToEdge()
        handler.postDelayed({
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }, 1500)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}