package com.tarunguptaraja.expensia.ui.auth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarunguptaraja.expensia.R
import com.tarunguptaraja.expensia.ui.auth.view.OnboardingModel

class OnboardingViewpagerAdapter(private val onboardingList: List<OnboardingModel>, val context: Context):RecyclerView.Adapter<OnboardingViewpagerAdapter.ViewPagerViewHolder>() {
    class ViewPagerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView = itemView.findViewById<ImageView>(R.id.titleImage)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)

        fun bindData(item: OnboardingModel){
            imageView.setImageResource(item.image)
            title.text= item.title
            description.text = item.description

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val item = onboardingList[position]
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return onboardingList.size
    }
}