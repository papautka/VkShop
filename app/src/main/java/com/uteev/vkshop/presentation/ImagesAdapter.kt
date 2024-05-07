package com.uteev.vkshop.presentation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uteev.vkshop.R

class ImagesAdapter(private val context: Context, private val images: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = trimQuotes(trimBrackets(images[position]))
        Log.d("onBindViewHolder", imageUrl)
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.place_holder_image)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    fun trimBrackets(input: String): String {
        var result = input
        if (result.startsWith("[")) {
            result = result.substring(1)
        }
        if (result.endsWith("]")) {
            result = result.substring(0, result.length - 1)
        }
        return result
    }

    fun trimQuotes(input: String): String {
        var result = input
        if (result.startsWith("\"")) {
            result = result.substring(1)
        }
        if (result.endsWith("\"")) {
            result = result.substring(0, result.length - 1)
        }
        return result
    }
}
