package com.tarunguptaraja.expensia.ui.budget.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarunguptaraja.expensia.base.BaseFragment
import com.tarunguptaraja.expensia.databinding.FragmentBudgetBinding

class BudgetFragment : BaseFragment() {
    private lateinit var binding: FragmentBudgetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}