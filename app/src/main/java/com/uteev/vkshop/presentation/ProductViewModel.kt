package com.uteev.vkshop.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.uteev.vkshop.data.api.ApiFactory
import com.uteev.vkshop.data.database.AppDatabase
import com.uteev.vkshop.domain.pojo.ProductApi
import com.uteev.vkshop.domain.pojo.ProductDB
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val application: Application,
    private val database: AppDatabase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val priceList = database.coinPriceInfoDao().getProductList()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getDetailInfo(id : Int) : LiveData<ProductDB> {
        return database.coinPriceInfoDao().getProduct(id)
    }

    fun updateProductList(listProductDB: List<ProductDB>) {
        Thread {
            database.coinPriceInfoDao().replaceProductList(listProductDB)
        }.start()
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
            database.coinPriceInfoDao().insertPriceList(products)
        }.start()
    }
    private var skip = 0 // Заданное значение параметра skip
    var limit = 20 // Заданное значение параметра limit

    fun loadData() {
        val disposable = ApiFactory.apiService.getProducts(limit = limit, skip = skip)
            .map { it.products ?: emptyList() }
            .flatMap { ApiFactory.apiService.getProducts(limit = limit, skip = skip) }
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                _isLoading.postValue(true)
            }
            .subscribe({ products ->
                Thread {
                    updateProductList(fromJsonToProductDB(products.products ?: emptyList()))
                }.start()
                Log.d("Добавили в базу данных", "loadData: $products")
                _isLoading.postValue(false)
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

    public fun resetIsLoading() {
        _isLoading.value = false
    }

}


