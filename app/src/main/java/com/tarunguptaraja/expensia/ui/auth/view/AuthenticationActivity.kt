package com.tarunguptaraja.expensia.ui.auth.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivityAuthenticationBinding
import com.tarunguptaraja.expensia.extensions.toJson
import com.tarunguptaraja.expensia.setupvm.store
import com.tarunguptaraja.expensia.ui.auth.viewmodel.LogInViewModel

class AuthenticationActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private val viewModel: LogInViewModel by store()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        enableEdgeToEdge()
        supportActionBar?.hide()

        viewModel.requestOtp { response ->
            Log.d("REQUEST OTP", response.toJson())
        }
    }
}