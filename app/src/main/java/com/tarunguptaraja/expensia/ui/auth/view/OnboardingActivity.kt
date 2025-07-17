package com.tarunguptaraja.expensia.ui.auth.view

import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivityOnboardingBinding
import com.tarunguptaraja.expensia.ui.auth.adapter.OnboardingViewpagerAdapter
import com.tarunguptaraja.expensia.ui.auth.model.OnboardingModel
import com.tarunguptaraja.expensia.utills.Constants

class OnboardingActivity : BaseActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    val onboardingViewpagerAdapter = OnboardingViewpagerAdapter()
    private var currentPage = 0
    private val scrollInterval: Long = 2500
    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private lateinit var autoScrollRunnable: Runnable
    private val realOnboardingList = listOf(
        OnboardingModel(
            image = R.drawable.onboarding_illust1,
            title = "Gain total control \nof your money",
            description = "Become your own money manager \nand make every cent count"
        ), OnboardingModel(
            image = R.drawable.onboarding_illust2,
            title = "Know where your \nmoney goes",
            description = "Track your transaction easily, \nwith categories and financial report"
        ), OnboardingModel(
            image = R.drawable.onboarding_illust3,
            title = "Planning ahead",
            description = "Setup your budget for each category \nso you are in control"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, AuthenticationActivity::class.java)
        binding.btnSignUp.setOnClickListener {
            intent.putExtra(Constants.IS_LOGIN, false)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
        binding.btnLogin.setOnClickListener {
            intent.putExtra(Constants.IS_LOGIN, true)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }
        setupViewPagerAdapter()
        val totalPages = realOnboardingList.size
        setupDots(totalPages)
        binding.slideViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
                updateDots(position)
                // If we're on fake page (last), instantly jump to real 0 (no animation)
                if (position == realOnboardingList.size) {
                    binding.slideViewPager.post {
                        binding.slideViewPager.setCurrentItem(0, false)
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> pauseAutoScroll()
                    ViewPager2.SCROLL_STATE_IDLE -> resumeAutoScrollWithDelay()
                }
            }
        })
    }

    fun setupViewPagerAdapter() {
        // Add a fake/duplicate first item at the end:
        val onboardingList = realOnboardingList + realOnboardingList.first()
        binding.slideViewPager.adapter = onboardingViewpagerAdapter.apply {
            submitList(onboardingList)
        }
    }

    private val dots = mutableListOf<View>()
    private fun setupDots(count: Int) {
        binding.dotsContainer.removeAllViews()
        dots.clear()

        for (i in 0 until count) { // Only the real pages
            val dot = View(this).apply {
                val size = if (i == 0) 16 else 8
                layoutParams = LinearLayout.LayoutParams(size.dp, size.dp).apply {
                    setMargins(6.dp, 0, 6.dp, 0)
                }
                background = ContextCompat.getDrawable(
                    this@OnboardingActivity,
                    if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive
                )
            }
            binding.dotsContainer.addView(dot)
            dots.add(dot)
        }
    }

    private fun updateDots(selectedPosition: Int) {
        val realDotsPos = selectedPosition % realOnboardingList.size // Ignore fake page for dot
        for (i in dots.indices) {
            val isSelected = i == realDotsPos
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
                val pageCount = realOnboardingList.size
                val adapterCount = onboardingViewpagerAdapter.itemCount
                if (adapterCount == 0) return
                // If at the fake page (last), animate to it, then will auto-correct to 0
                if (currentPage < adapterCount - 1) {
                    currentPage++
                    smoothScrollToNextPageSmoothly()
                } else {
                    // Animate to duplicate page, onPageSelected will handle the rest
                    currentPage++
                    smoothScrollToNextPageSmoothly()
                }
                autoScrollHandler.postDelayed(this, scrollInterval)
            }
        }
        autoScrollHandler.postDelayed(autoScrollRunnable, scrollInterval)
    }

    private fun pauseAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    private fun resumeAutoScrollWithDelay(delay: Long = scrollInterval) {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
        autoScrollHandler.postDelayed(autoScrollRunnable, delay)
    }

    private fun smoothScrollToNextPageSmoothly() {
        val viewPager = binding.slideViewPager
        val pageWidth = viewPager.width
        if (viewPager.beginFakeDrag()) {
            val animator = ValueAnimator.ofFloat(0f, -pageWidth.toFloat())
            animator.duration = 600 // in ms, adjust as needed
            animator.interpolator = AccelerateDecelerateInterpolator()
            var previousValue = 0f
            animator.addUpdateListener { animation ->
                val currentValue = animation.animatedValue as Float
                viewPager.fakeDragBy(currentValue - previousValue)
                previousValue = currentValue
            }
            animator.addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {}
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    viewPager.endFakeDrag()
                    viewPager.setCurrentItem(currentPage, false)
                }

                override fun onAnimationCancel(animation: android.animation.Animator) {
                    viewPager.endFakeDrag()
                }

                override fun onAnimationRepeat(animation: android.animation.Animator) {}
            })
            animator.start()
        }
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