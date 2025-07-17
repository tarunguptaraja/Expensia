package com.tarunguptaraja.expensia.ui.auth.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivityAuthenticationBinding
import com.tarunguptaraja.expensia.extensions.buildColoredText
import com.tarunguptaraja.expensia.extensions.hideKeyboard
import com.tarunguptaraja.expensia.extensions.onOneClick
import com.tarunguptaraja.expensia.extensions.putString
import com.tarunguptaraja.expensia.setupvm.store
import com.tarunguptaraja.expensia.ui.auth.viewmodel.LogInViewModel
import com.tarunguptaraja.expensia.ui.home.view.HomeActivity
import com.tarunguptaraja.expensia.utills.Constants
import com.tarunguptaraja.expensia.utills.Constants.AuthState
import java.util.Locale

class AuthenticationActivity : BaseActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private val viewModel: LogInViewModel by store()
    private val pageState = MutableLiveData<AuthState>()
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private var isPasswordVisible = false
    private var timer: CountDownTimer? = null
    private val isLogIn by lazy { intent.getBooleanExtra(Constants.IS_LOGIN, false) }
    private var isBackNavigation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        if (isLogIn) pageState.postValue(AuthState.LOGIN)
        else pageState.postValue(AuthState.SIGNUP)
        binding.toolbar.ivBack.onOneClick {
            finish()
        }

        pageState.observe(this) {
            isPasswordVisible = false
            when (it) {
                AuthState.SIGNUP -> {
                    animateInFromRight(binding.clSignUp, binding.clLogIn)
                    binding.clSignUp.visibility = View.VISIBLE
                    binding.clLogIn.visibility = View.INVISIBLE
                    binding.clForgetPassword.visibility = View.INVISIBLE
                    binding.clEmailSentAway.visibility = View.INVISIBLE
                    binding.clResetPassword.visibility = View.INVISIBLE
                    binding.clVerifyOtp.visibility = View.INVISIBLE
                    binding.toolbar.tvTitle.text = getString(R.string.sign_up)
                }

                AuthState.LOGIN -> {
                    if (isBackNavigation) animateInFromLeft(binding.clLogIn, binding.clSignUp)
                    else animateInFromRight(binding.clLogIn, binding.clSignUp)

                    binding.clSignUp.visibility = View.INVISIBLE
                    binding.clLogIn.visibility = View.VISIBLE
                    binding.clForgetPassword.visibility = View.INVISIBLE
                    binding.clEmailSentAway.visibility = View.INVISIBLE
                    binding.clResetPassword.visibility = View.INVISIBLE
                    binding.clVerifyOtp.visibility = View.INVISIBLE
                    binding.toolbar.tvTitle.text = getString(R.string.login)
                }

                AuthState.VERIFY_OTP -> {
                    animateInFromRight(binding.clVerifyOtp, binding.clLogIn)
                    binding.clSignUp.visibility = View.INVISIBLE
                    binding.clLogIn.visibility = View.INVISIBLE
                    binding.clForgetPassword.visibility = View.INVISIBLE
                    binding.clEmailSentAway.visibility = View.INVISIBLE
                    binding.clResetPassword.visibility = View.INVISIBLE
                    binding.clVerifyOtp.visibility = View.VISIBLE
                    binding.toolbar.tvTitle.text = getString(R.string.verification)
                    startCountDownTimer()
                    binding.tvVerificationMgs.text = buildColoredText(
                        listOf(
                            "We send verification code to your email ",
                            email,
                            ". You can check your inbox ."
                        ), listOf(
                            getColor(R.color.dark_100),
                            getColor(R.color.violet_100),
                            getColor(R.color.dark_100)
                        )
                    )
                }

                AuthState.FORGOT_PASSWORD -> {
                    animateInFromRight(binding.clForgetPassword, binding.clLogIn)
                    binding.clSignUp.visibility = View.INVISIBLE
                    binding.clLogIn.visibility = View.INVISIBLE
                    binding.clForgetPassword.visibility = View.VISIBLE
                    binding.clEmailSentAway.visibility = View.INVISIBLE
                    binding.clResetPassword.visibility = View.INVISIBLE
                    binding.clVerifyOtp.visibility = View.INVISIBLE
                    binding.toolbar.tvTitle.text = getString(R.string.forgot_password)
                }

                AuthState.RESET_PASSWORD -> {
                    animateInFromRight(binding.clResetPassword, binding.clForgetPassword)
                    binding.clSignUp.visibility = View.INVISIBLE
                    binding.clLogIn.visibility = View.INVISIBLE
                    binding.clForgetPassword.visibility = View.INVISIBLE
                    binding.clEmailSentAway.visibility = View.INVISIBLE
                    binding.clResetPassword.visibility = View.VISIBLE
                    binding.clVerifyOtp.visibility = View.INVISIBLE
                    binding.toolbar.tvTitle.text = getString(R.string.reset_password)
                }
            }
            isBackNavigation = false
        }

        binding.btnSignUp.onOneClick {
            if (binding.signUpName.text.isEmpty() || binding.signUpEmail.text.isEmpty() || binding.signUpPassword.text.isEmpty()) {
                Toast.makeText(this, "Entries are missing", Toast.LENGTH_LONG).show()
                return@onOneClick
            }
            if (!binding.signUpCheckBox.isChecked) {
                Toast.makeText(this, "Have to with our T&C and Privacy Policy", Toast.LENGTH_LONG)
                    .show()
                return@onOneClick
            }
            name = binding.signUpName.text.toString().trim()
            email = binding.signUpEmail.text.toString().trim()
            password = binding.signUpPassword.text.toString().trim()
            hideKeyboard()
            viewModel.requestOtp(email) { response ->
                if (response.success) {
                    pageState.postValue(AuthState.VERIFY_OTP)
                } else {
                    Toast.makeText(this, response.description, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.tvForgotPassword.onOneClick {
            hideKeyboard()
            pageState.postValue(AuthState.FORGOT_PASSWORD)
        }

        binding.llChangeSignUp.onOneClick {
            hideKeyboard()
            pageState.postValue(AuthState.SIGNUP)
        }

        binding.btnVerifyOtp.onOneClick {
            if (binding.otpPinView.text.isNullOrEmpty() || binding.otpPinView.text.toString().length != 6) {
                Toast.makeText(this, "OTP is incorrect", Toast.LENGTH_LONG).show()
                return@onOneClick
            }
            email = binding.signUpEmail.text.toString().trim()
            password = binding.signUpPassword.text.toString().trim()
            hideKeyboard()
            viewModel.signUp(name, email, password, binding.otpPinView.text.toString()) { res ->
                if (res.success) {
                    putString(Constants.JWT_TOKEN, res.data.token)
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, res.description, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnContinue.onOneClick {
            if (binding.forgetEmail.text.isNullOrEmpty()) {
                Toast.makeText(this, "Email is empty", Toast.LENGTH_LONG).show()
                return@onOneClick
            }
            hideKeyboard()
            email = binding.forgetEmail.text.toString()
            viewModel.forgotPassword(email) { response ->
                if (response.success) {
                    animateInFromRight(binding.clEmailSentAway, binding.clForgetPassword)
                    binding.tvEmailSentDescription.text =
                        "Check your email $email and follow the instructions to reset your password"
                    binding.clEmailSentAway.visibility = View.VISIBLE
                    binding.clForgetPassword.visibility = View.INVISIBLE
                } else {
                    Toast.makeText(this, response.description, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnBackToLogin.onOneClick {
            isBackNavigation = true
            pageState.postValue(AuthState.LOGIN)
        }

        binding.resetContinue.onOneClick {
            if (binding.resetNewPassword.text.isNullOrEmpty() || binding.resetRetypePassword.text.isNullOrEmpty()) {
                Toast.makeText(this, "Entries are missing", Toast.LENGTH_LONG).show()
                return@onOneClick
            }
            if (binding.resetNewPassword.text.toString() != binding.resetRetypePassword.text.toString()) {
                Toast.makeText(this, "Password Mismatched", Toast.LENGTH_LONG).show()
                return@onOneClick
            }
            hideKeyboard()
            password = binding.resetNewPassword.text.toString()
            viewModel.resetPassword("", password) { response ->
                if (response.success) {
                    startActivity(Intent(this, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, response.description, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.tvSendAgain.onOneClick {
            viewModel.requestOtp(email) { response ->
                if (response.success) {
                    startCountDownTimer()
                } else {
                    Toast.makeText(this, response.description, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.btnLogIn.onOneClick {
            if (binding.logInEmail.text.isNullOrEmpty() || binding.logInPassword.text.isNullOrEmpty()) {
                Toast.makeText(this, "Entries are missing", Toast.LENGTH_LONG).show()
                return@onOneClick
            }
            hideKeyboard()
            email = binding.logInEmail.text.toString()
            password = binding.logInPassword.text.toString()
            viewModel.logIn(email, password) { response ->
                if (response.success) {
                    if (response.data.resetPassword) {
                        pageState.postValue(AuthState.RESET_PASSWORD)
                    } else {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this, response.description, Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.logInPasswordEye.onOneClick {
            isPasswordVisible = !isPasswordVisible

            val typeface = binding.logInPassword.typeface
            val selection = binding.logInPassword.selectionStart
            if (isPasswordVisible) {
                binding.logInPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.logInPasswordEye.setColorFilter(
                    ContextCompat.getColor(
                        this, R.color.violet_100
                    )
                )
            } else {
                binding.logInPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.logInPasswordEye.setColorFilter(
                    ContextCompat.getColor(
                        this, R.color.light_20
                    )
                )
            }
            binding.logInPassword.typeface = typeface
            binding.logInPassword.setSelection(selection)
        }

        binding.signUpPasswordEye.onOneClick {
            isPasswordVisible = !isPasswordVisible

            val typeface = binding.signUpPassword.typeface
            val selection = binding.signUpPassword.selectionStart
            if (isPasswordVisible) {
                binding.signUpPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.signUpPasswordEye.setColorFilter(
                    ContextCompat.getColor(
                        this, R.color.violet_100
                    )
                )
            } else {
                binding.signUpPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.signUpPasswordEye.setColorFilter(
                    ContextCompat.getColor(
                        this, R.color.light_20
                    )
                )
            }
            binding.signUpPassword.typeface = typeface
            binding.signUpPassword.setSelection(selection)
        }


        binding.btnGoogleSignUp.onOneClick {
            hideKeyboard()
        }

        binding.llChangeLogIn.onOneClick {
            hideKeyboard()
            pageState.postValue(AuthState.LOGIN)
        }
    }

    fun startCountDownTimer() {
        binding.tvResend.visibility = View.VISIBLE
        binding.tvSendAgain.visibility = View.INVISIBLE
        timer?.cancel()
        timer = object : CountDownTimer(59999, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                binding.tvResend.text = "00:${String.format("%02d", seconds, Locale.ENGLISH)}"
            }

            override fun onFinish() {
                binding.tvResend.visibility = View.INVISIBLE
                binding.tvSendAgain.visibility = View.VISIBLE
            }
        }
        timer?.start()
    }

    private fun animateInFromRight(newView: View, oldView: View) {
        oldView.animate().translationX(-oldView.width.toFloat()).alpha(0f).setDuration(400)
            .withEndAction {
                oldView.visibility = View.INVISIBLE
                oldView.translationX = 0f
                oldView.alpha = 1f
            }.start()
        newView.translationX = newView.width.toFloat()
        newView.alpha = 0f
        newView.visibility = View.VISIBLE
        newView.animate().translationX(0f).alpha(1f).setDuration(400).start()
    }

    private fun animateInFromLeft(newView: View, oldView: View) {
        oldView.animate().translationX(oldView.width.toFloat()).alpha(0f).setDuration(400)
            .withEndAction {
                oldView.visibility = View.INVISIBLE
                oldView.translationX = 0f
                oldView.alpha = 1f
            }.start()
        newView.translationX = -newView.width.toFloat()
        newView.alpha = 0f
        newView.visibility = View.VISIBLE
        newView.animate().translationX(0f).alpha(1f).setDuration(400).start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}