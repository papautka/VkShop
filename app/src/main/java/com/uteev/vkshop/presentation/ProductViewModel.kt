package com.uteev.vkshop.presentation

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.uteev.vkshop.R
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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getDetailInfo(id : Int) : LiveData<ProductDB> {
        return db.coinPriceInfoDao().getProduct(id)
    }

    fun updateProductList(listProductDB: List<ProductDB>) {
        Thread {
            db.coinPriceInfoDao().replaceProductList(listProductDB)
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
            db.coinPriceInfoDao().insertPriceList(products)
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


