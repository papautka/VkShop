package com.uteev.vkshop.domain.pojo

import androidx.room.Entity


@Entity(tableName = "productsDB")
data class ProductDB (
    val id: Long?,

    val title: String?,

    val description: String?,

    val price: Long?,

    val discountPercentage: Double?,

    val rating: Double?,

    val stock: Long?,

    val brand: String?,

    val category: String?,

    val thumbnail: String?,

    val images: String?
)
