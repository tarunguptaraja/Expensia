package com.tarunguptaraja.expensia.ui.transaction.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tarunguptaraja.expensia.base.BaseFragment
import com.tarunguptaraja.expensia.databinding.FragmentTransactionsBinding

class TransactionsFragment : BaseFragment() {

    private lateinit var binding: FragmentTransactionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}