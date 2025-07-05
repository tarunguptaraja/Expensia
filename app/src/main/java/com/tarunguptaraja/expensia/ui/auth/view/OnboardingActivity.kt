package com.tarunguptaraja.expensia.ui.auth.view

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivityOnboardingBinding
import com.tarunguptaraja.expensia.ui.auth.adapter.OnboardingViewpagerAdapter

class OnboardingActivity : BaseActivity() {
    lateinit var onboardingViewpagerAdapter: OnboardingViewpagerAdapter
    private lateinit var binding: ActivityOnboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPagerAdapter()
        binding.slideViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                changeColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                changeColor()
                super.onPageScrollStateChanged(state)
            }
        })
    }

    fun setupViewPagerAdapter() {
        val imageList = listOf(
            R.drawable.onboarding_illust1,
            R.drawable.onboarding_illust2,
            R.drawable.onboarding_illust3
        )
        val titleList = listOf(
            "Gain total control \n" + "of your money",
            "Know where your \n " + "money goes",
            "Planning ahead"
        )
        val discriptionList = listOf(
            "Become your own money manager \n" + " and make every cent count",
            "Track your transaction easily, \n" + "  with categories and financial report",
            "Setup your budget for each category \n" + "  so you in control"
        )
        onboardingViewpagerAdapter =
            OnboardingViewpagerAdapter(imageList, this, titleList, discriptionList)
        binding.slideViewPager.adapter = onboardingViewpagerAdapter
    }

    fun changeColor() {
        when (binding.slideViewPager.currentItem) {
            0 -> {
                binding.iv1.setBackgroundColor(resources.getColor(R.color.violet_100, null))
                binding.iv2.setBackgroundColor(resources.getColor(R.color.violet_40, null))
                binding.iv3.setBackgroundColor(resources.getColor(R.color.violet_40, null))
            }

            1 -> {
                binding.iv1.setBackgroundColor(resources.getColor(R.color.violet_40, null))
                binding.iv2.setBackgroundColor(resources.getColor(R.color.violet_100, null))
                binding.iv3.setBackgroundColor(resources.getColor(R.color.violet_40, null))
            }

            2 -> {
                binding.iv1.setBackgroundColor(resources.getColor(R.color.violet_40, null))
                binding.iv2.setBackgroundColor(resources.getColor(R.color.violet_40, null))
                binding.iv3.setBackgroundColor(resources.getColor(R.color.violet_100, null))
            }
        }
    }
}