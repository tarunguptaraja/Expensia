package com.tarunguptaraja.expensia.ui.auth.adapter

import com.tarunguptaraja.expensia.databinding.SliderLayoutBinding
import com.tarunguptaraja.expensia.ui.auth.model.OnboardingModel
import com.tarunguptaraja.expensia.utills.EasyBindingAdapter

class OnboardingViewpagerAdapter : EasyBindingAdapter<OnboardingModel, SliderLayoutBinding>(
    SliderLayoutBinding::inflate
) {
    override fun onBindViewHolder(holder: Holder<SliderLayoutBinding>, position: Int) {
        val item = getItem(position)
        holder.binding.tvTitle.text = item.title
        holder.binding.tvDescription.text = item.description
        holder.binding.titleImage.setImageResource(item.image)
    }
}