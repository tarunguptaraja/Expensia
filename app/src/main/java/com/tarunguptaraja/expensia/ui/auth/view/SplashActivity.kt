package com.tarunguptaraja.expensia.ui.auth.view

import android.os.Bundle
import android.widget.Toast
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        Toast.makeText(this,"HI Tarun bhai",Toast.LENGTH_SHORT).show()
    }
}