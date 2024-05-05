package com.uteev.vkshop.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uteev.vkshop.R

class ProductListActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productListAdapter: ProductListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        setupRecyclerView()
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        productViewModel.priceList.observe(this) {
            Log.d("TAG", it.toString())
            productListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        val rvProduct = findViewById<RecyclerView>(R.id.rv_list_product)
        installRv(rvProduct)
    }

    private fun installRv(rvProduct : RecyclerView) {
        with(rvProduct) {
            layoutManager = GridLayoutManager(context, 1)
            productListAdapter = ProductListAdapter()
            adapter = productListAdapter
        }

    }

}