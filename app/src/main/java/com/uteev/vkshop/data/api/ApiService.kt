package com.uteev.vkshop.data.api

import com.uteev.vkshop.domain.pojo.ListProduct
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getProducts(
        @Query(QUERY_PARAM_API_KEY) apiKey: String = "",
        @Query(QUERY_PARAM_SKIP) skip: Int,
        @Query(QUERY_PARAM_LIMIT) limit: Int
    ): Single<ListProduct>

    companion object {
        const val QUERY_PARAM_API_KEY = "api_key"
        const val QUERY_PARAM_SKIP = "skip"
        const val QUERY_PARAM_LIMIT = "limit"
    }
}

