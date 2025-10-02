package com.tarunguptaraja.expensia.ui.home.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.base.BaseActivity
import com.tarunguptaraja.expensia.databinding.ActivityHomeBinding
import com.tarunguptaraja.expensia.extensions.onOneClick
import com.tarunguptaraja.expensia.extensions.replaceFragment
import com.tarunguptaraja.expensia.ui.budget.view.BudgetFragment
import com.tarunguptaraja.expensia.ui.profile.view.ProfileFragment
import com.tarunguptaraja.expensia.ui.transaction.view.TransactionsFragment

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setHomeFragment()

        binding.homeTab.onOneClick {
            setHomeFragment()
        }
        binding.transactionTab.onOneClick {
            setTransactionFragment()
        }

        binding.budgetTab.onOneClick {
            setBudgetFragment()
        }
        binding.profileTab.onOneClick {
            setProfileFragment()
        }

    }

    private fun setHomeFragment() {
        replaceFragment(HomeFragment(), R.id.fragment_container)
    }

    private fun setTransactionFragment() {
        replaceFragment(TransactionsFragment(), R.id.fragment_container)
    }

    private fun setBudgetFragment() {
        replaceFragment(BudgetFragment(), R.id.fragment_container)
    }

    private fun setProfileFragment() {
        replaceFragment(ProfileFragment(), R.id.fragment_container)
    }

}