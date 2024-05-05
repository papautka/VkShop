package com.uteev.vkshop.data.api

import com.uteev.vkshop.domain.pojo.ListProduct
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getProducts() : Single<ListProduct>
}