package com.uteev.vkshop.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.uteev.vkshop.R
import com.uteev.vkshop.domain.pojo.ProductDB
import kotlin.coroutines.coroutineContext

class ProductListAdapter : ListAdapter<ProductDB, ProductItemViewHolder>(ProductItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val layout = R.layout.item_product
        Log.d("TAG", "onCreateViewHolder: $layout")
        val viewProductDB = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ProductItemViewHolder(viewProductDB)
    }

    override fun onBindViewHolder(viewHolder: ProductItemViewHolder, position: Int) {
        val product = getItem(position)
        with(viewHolder) {
            tvTitle.text=product.title.toString()
            tvDescription.text=product.description.toString()
            tvPrice.text=product.price.toString()
            Picasso.get().load(product.thumbnail).into(viewHolder.ivImage)
        }
    }
}