package com.uteev.vkshop.presentation.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uteev.vkshop.R
import com.uteev.vkshop.presentation.ProductApp
import com.uteev.vkshop.presentation.ProductViewModel
import com.uteev.vkshop.presentation.ViewModelFactory
import com.uteev.vkshop.presentation.adapter.ProductListAdapter
import javax.inject.Inject

class ProductListActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel
    private lateinit var productListAdapter: ProductListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as ProductApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)

        if (!isNetworkAvailable()) {
            startNoConnectActivity()
        } else {
            startProductList()
        }
    }

    private fun startProductList() {
        setContentView(R.layout.activity_product_list)
        setupRecyclerView()
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        productViewModel.priceList.observe(this) {
            Log.d("TAG", it.toString())
            productListAdapter.submitList(it)
        }
        checkScrollView()
    }


    private val updateDataReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            productViewModel.loadData()
        }
    }

    private fun startNoConnectActivity() {
        Log.d("TAG", "No internet connection")
        val intentNoConnect = NoConnectionActivity.newIntent(this)
        startActivity(intentNoConnect)
    }

    private fun checkScrollView() {
        val scrollView = findViewById<NestedScrollView>(R.id.scrollView)
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            val viewPage = scrollView.getChildAt(scrollView.childCount - 1)
            val diff = viewPage.bottom - (scrollView.height + scrollView.scrollY)
            if (diff == 0) {
                // прокрутка достигла нижнего края контента
                productViewModel.limit += 20
                productViewModel.loadData()
                showProgressBar()
                Log.d("checkScrollView", "bottom")
            }
            if(!isNetworkAvailable()) {
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                progressBar.visibility = ProgressBar.GONE
                startNoConnectActivity()
            }
        }
    }

    private fun showProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        productViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                progressBar.visibility = ProgressBar.VISIBLE
            } else {
                progressBar.visibility = ProgressBar.GONE
            }
        })
    }


    private fun setupRecyclerView() {
        val rvProduct = findViewById<RecyclerView>(R.id.rv_list_product)
        installRv(rvProduct)
        setupOnClickListener()
    }

    private fun installRv(rvProduct : RecyclerView) {
        with(rvProduct) {
            layoutManager = GridLayoutManager(context, 1)
            productListAdapter = ProductListAdapter()
            adapter = productListAdapter
        }
    }
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onDestroy() {
        super.onDestroy()
        // Отменяем регистрацию приемника широковещательных сообщений при уничтожении активности
        unregisterReceiver(updateDataReceiver)
    }

    private fun setupOnClickListener() {
        productListAdapter.onProductItemClick = {
            val intentElement = ProductElementActivity.newIntent(this, it)
            startActivity(intentElement)
        }
    }

}