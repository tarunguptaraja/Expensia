package com.tarunguptaraja.expensia.ui.auth.view

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.viewpager2.widget.ViewPager2
import com.tarunguptaraja.expensia.Expensia
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivityOnboardingBinding
import com.tarunguptaraja.expensia.ui.auth.adapter.OnboardingViewpagerAdapter

class OnboardingActivity : BaseActivity() {
    lateinit var onboardingViewpagerAdapter: OnboardingViewpagerAdapter
    private lateinit var binding: ActivityOnboardingBinding
    private var currentPage = 0
    private var isScrollingForward = true
    private val scrollInterval: Long = 5000 // 3 seconds
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private lateinit var autoScrollRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,AuthenticationActivity::class.java))
            finish()
        }
        binding.btnLogin.setOnClickListener {

        }
        Expensia.sharedPreferences.edit() {
            putBoolean("onboarding_seen", true)
        }
        setupViewPagerAdapter()
        val totalPages = binding.slideViewPager.adapter?.itemCount ?: 0
        setupDots(totalPages)
        binding.slideViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position // Sync current page index
                updateDots(position)
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })


    }

    fun setupViewPagerAdapter() {
        val onboardingList = listOf(
            OnboardingModel(
                image = R.drawable.onboarding_illust1,
                title = "Gain total control \nof your money",
                description = "Become your own money manager \nand make every cent count"
            ),
            OnboardingModel(
                image = R.drawable.onboarding_illust2,
                title = "Know where your \nmoney goes",
                description = "Track your transaction easily, \nwith categories and financial report"
            ),
            OnboardingModel(
                image = R.drawable.onboarding_illust3,
                title = "Planning ahead",
                description = "Setup your budget for each category \nso you are in control"
            )
        )
        onboardingViewpagerAdapter = OnboardingViewpagerAdapter(onboardingList, this)
        binding.slideViewPager.adapter = onboardingViewpagerAdapter
    }
    private val dots = mutableListOf<View>()
    private fun setupDots(count: Int) {
        binding.dotsContainer.removeAllViews()
        dots.clear()

        for (i in 0 until count) {
            val dot = View(this).apply {
                val size = if (i == 0) 16 else 8 // First one bigger
                layoutParams = LinearLayout.LayoutParams(size.dp, size.dp).apply {
                    setMargins(6.dp, 0, 6.dp, 0)
                }
                background = ContextCompat.getDrawable(this@OnboardingActivity, if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive)
            }
            binding.dotsContainer.addView(dot)
            dots.add(dot)
        }
    }

    private fun updateDots(selectedPosition: Int) {
        for (i in dots.indices) {
            val isSelected = i == selectedPosition
            val size = if (isSelected) 16 else 8

            dots[i].layoutParams = LinearLayout.LayoutParams(size.dp, size.dp).apply {
                setMargins(6.dp, 0, 6.dp, 0)
            }

            dots[i].background = ContextCompat.getDrawable(
                this@OnboardingActivity,
                if (isSelected) R.drawable.dot_active else R.drawable.dot_inactive
            )
            dots[i].requestLayout()
        }
    }

    val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private fun startAutoScroll() {
        autoScrollRunnable = object : Runnable {
            override fun run() {
                val itemCount = binding.slideViewPager.adapter?.itemCount ?: 0
                if (itemCount == 0) return

                // Update scroll direction
                if (isScrollingForward) {
                    if (currentPage < itemCount - 1) {
                        currentPage++
                    } else {
                        isScrollingForward = false
                        currentPage--
                    }
                } else {
                    if (currentPage > 0) {
                        currentPage--
                    } else {
                        isScrollingForward = true
                        currentPage++
                    }
                }

                binding.slideViewPager.setCurrentItem(currentPage, true)

                autoScrollHandler.postDelayed(this, scrollInterval)
            }
        }

        autoScrollHandler.postDelayed(autoScrollRunnable, scrollInterval)
    }
    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onPause() {
        super.onPause()
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }
}