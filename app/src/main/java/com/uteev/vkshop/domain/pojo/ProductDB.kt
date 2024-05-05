package com.uteev.vkshop.domain.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "productsDB")
data class ProductDB (
    @PrimaryKey(autoGenerate = false)
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "price")
    val price: Long?,

    @ColumnInfo(name = "discountPercentage")
    val discountPercentage: Double?,

    @ColumnInfo(name = "rating")
    val rating: Double?,

    @ColumnInfo(name = "stock")
    val stock: Long?,

    @ColumnInfo(name = "brand")
    val brand: String?,

    @ColumnInfo(name = "category")
    val category: String?,

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String?,

    @ColumnInfo(name = "images")
    val images: String?
)
