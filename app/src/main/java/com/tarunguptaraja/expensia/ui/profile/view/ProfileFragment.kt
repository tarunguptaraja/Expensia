package com.tarunguptaraja.expensia.ui.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tarunguptaraja.expensia.base.BaseFragment
import com.tarunguptaraja.expensia.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}