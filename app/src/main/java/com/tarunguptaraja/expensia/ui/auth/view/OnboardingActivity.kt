package com.tarunguptaraja.expensia.ui.auth.view

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.tarunguptaraja.expensia.R

class OnboardingActivity : AppCompatActivity() {
    lateinit var iv1: ImageView
    lateinit var iv2: ImageView
    lateinit var iv3: ImageView
    lateinit var onboardingViewpager: ViewPager2
    lateinit var onboardingViewpagerAdapter: OnboardingViewpagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)
        onboardingViewpager = findViewById(R.id.slideViewPager)
        setupViewPagerAdapter()
        onboardingViewpager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
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
        val imageList =
            listOf(R.drawable.onboarding_illust1, R.drawable.onboarding_illust2, R.drawable.onboarding_illust3)
        val titleList = listOf("Gain total control \n" + "of your money","Know where your \n " + "money goes","Planning ahead")
        val discriptionList = listOf( "Become your own money manager \n" +
                " and make every cent count","Track your transaction easily, \n" +
                "  with categories and financial report", "Setup your budget for each category \n" +
                "  so you in control")
        onboardingViewpagerAdapter = OnboardingViewpagerAdapter(imageList, this,titleList,discriptionList)
        onboardingViewpager.adapter = onboardingViewpagerAdapter
    }

    fun changeColor() {
        when (onboardingViewpager.currentItem) {
            0 -> {
                iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_100))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_40))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_40))

            }

            1 -> { iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_40))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_100))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_40))


            }


            2 -> { iv1.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_40))
                iv2.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_40))
                iv3.setBackgroundColor(applicationContext.resources.getColor(R.color.violet_100))


            }
        }

    }


}