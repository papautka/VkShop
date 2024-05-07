package com.uteev.vkshop.presentation.adapter

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.uteev.vkshop.R
import com.uteev.vkshop.domain.pojo.ProductDB

class ProductListAdapter : ListAdapter<ProductDB, ProductItemViewHolder>(ProductItemDiffCallback()) {

    var onProductItemClick : ((ProductDB) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val layout = R.layout.item_product
        val viewProductDB = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ProductItemViewHolder(viewProductDB)
    }

    override fun onBindViewHolder(viewHolder: ProductItemViewHolder, position: Int) {
        val product = getItem(position)
        with(viewHolder) {
            view.setOnClickListener {
                onProductItemClick?.invoke(product)
            }
            tvTitle.text=product.title.toString()
            tvDescription.text=product.description.toString()
            tvPrice.text = buildString {
                append("Цена:")
                append(product.price)
                append("$")
            }
            Picasso.get().load(product.thumbnail).into(viewHolder.ivImage)
        }
    }
}