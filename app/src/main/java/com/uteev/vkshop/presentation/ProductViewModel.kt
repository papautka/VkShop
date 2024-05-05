package com.uteev.vkshop.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uteev.vkshop.data.api.ApiFactory
import com.uteev.vkshop.domain.pojo.ProductDB
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val productsDB = mutableListOf<ProductDB>()


    private fun loadData() {
        val disposable = ApiFactory.apiService.getProducts()
            .map { it.products ?: emptyList() } // Преобразуем null в пустой список
            .delaySubscription(10, TimeUnit.SECONDS)
            .repeat() // Повторяем запрос, если успешно
            .retry() // Повторяем запрос, если не успешно
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ products ->
                for (product in products) {
                    val listImage = product.images
                    val gson = Gson()
                    val stringImage = gson.toJson(listImage)
                    Log.d("IMAGE", stringImage)
                }
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
