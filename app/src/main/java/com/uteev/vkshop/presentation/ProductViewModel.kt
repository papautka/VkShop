package com.uteev.vkshop.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.uteev.vkshop.data.api.ApiFactory
import com.uteev.vkshop.data.database.AppDatabase
import com.uteev.vkshop.domain.pojo.ProductApi
import com.uteev.vkshop.domain.pojo.ProductDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()

    private val db = AppDatabase.getInstance(application)
    val priceList = db.coinPriceInfoDao().getProductList()

    fun getDetailInfo(id : Int) : LiveData<ProductDB> {
        return db.coinPriceInfoDao().getProduct(id)
    }
    private fun fromJsonToProductDB(products: List<ProductApi>): List<ProductDB> {
        val listProductDB = mutableListOf<ProductDB>()
        for (product in products) {
            val listImage = product.images
            val gson = Gson()
            val stringImage = gson.toJson(listImage)
            val productDB = ProductDB(product.id, product.title, product.description, product.price,
                product.discountPercentage, product.rating, product.stock, product.brand,
                product.category, product.thumbnail, stringImage)
            listProductDB.add(productDB)
        }
        return listProductDB
    }

    private fun insertProductsDB(products: List<ProductDB>) {
        Thread {
            db.coinPriceInfoDao().insertPriceList(products)
        }.start()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getProducts()
            .map { it.products ?: emptyList() }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ products ->
                insertProductsDB(fromJsonToProductDB(products))
            }, { error ->
                Log.d("ERROR", error.message ?: "Unknown error")
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    init {
        loadData()
    }
}
