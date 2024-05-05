package com.uteev.vkshop.domain.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ProductApi (
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int?,

    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

    @SerializedName("price")
    @Expose
    val price: Long?,

    @SerializedName("discountPercentage")
    @Expose
    val discountPercentage: Double?,

    @SerializedName("rating")
    @Expose
    val rating: Double?,

    @SerializedName("stock")
    @Expose
    val stock: Long?,

    @SerializedName("brand")
    @Expose
    val brand: String?,

    @SerializedName("category")
    @Expose
    val category: String?,

    @SerializedName("thumbnail")
    @Expose
    val thumbnail: String?,

    @SerializedName("images")
    @Expose
    val images: List<String>?
)
