package com.uteev.vkshop.presentation

import androidx.recyclerview.widget.DiffUtil
import com.uteev.vkshop.domain.pojo.ProductDB

class ProductItemDiffCallback : DiffUtil.ItemCallback<ProductDB>() {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun areItemsTheSame(oldItem: ProductDB, newItem: ProductDB): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ProductDB, newItem: ProductDB): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ProductDB, newItem: ProductDB): Any? {
        return super.getChangePayload(oldItem, newItem)
    }

}