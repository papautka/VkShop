package com.uteev.vkshop.domain.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListProduct (
    @SerializedName("products")
    @Expose
    val products: List<ProductApi>?,

    @SerializedName("total")
    @Expose
    val total: Long?,

    @SerializedName("skip")
    @Expose
    val skip: Long?,

    @SerializedName("limit")
    @Expose
    val limit: Long?
)