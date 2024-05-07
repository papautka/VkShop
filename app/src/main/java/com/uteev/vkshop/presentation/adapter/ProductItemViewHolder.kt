package com.uteev.vkshop.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uteev.vkshop.R

class ProductItemViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    val ivImage = view.findViewById<ImageView>(R.id.iLogoProduct)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
}