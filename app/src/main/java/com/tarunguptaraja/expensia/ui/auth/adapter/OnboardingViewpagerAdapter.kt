package com.tarunguptaraja.expensia.ui.auth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarunguptaraja.expensia.R

class OnboardingViewpagerAdapter(
    private val imageList: List<Int>,
    val context: Context,
    private val titlelist: List<String>,
    private val discriptionList: List<String>
) : RecyclerView.Adapter<OnboardingViewpagerAdapter.ViewPagerViewHolder>() {
    class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.titleImage)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val description = itemView.findViewById<TextView>(R.id.tv_description)

        fun bindData(image: Int, text1: String, text2: String) {
            imageView.setImageResource(image)
            title.text = text1
            description.text = text2

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.slider_layout, parent, false)
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val item = imageList[position]
        val item2 = titlelist[position]
        val item3 = discriptionList[position]
        holder.bindData(item, item2, item3)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}