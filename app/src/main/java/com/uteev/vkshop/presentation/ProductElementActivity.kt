package com.uteev.vkshop.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uteev.vkshop.R
import com.uteev.vkshop.data.database.AppDatabase
import com.uteev.vkshop.domain.pojo.ProductDB
import java.text.Format

class ProductElementActivity : AppCompatActivity() {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewPrice: TextView
    private lateinit var textViewDiscount: TextView
    private lateinit var textViewRating: TextView
    private lateinit var textViewStock: TextView
    private lateinit var textViewBrand: TextView
    private lateinit var textViewCategory: TextView

    private lateinit var imagesAdapter: ImagesAdapter

    private fun initView() {
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewDescription = findViewById(R.id.textViewDescription)
        textViewPrice = findViewById(R.id.textViewPrice)
        textViewDiscount = findViewById(R.id.textViewDiscount)
        textViewRating = findViewById(R.id.textViewRating)
        textViewStock = findViewById(R.id.textViewStock)
        textViewBrand = findViewById(R.id.textViewBrand)
        textViewCategory = findViewById(R.id.textViewCategory)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_element)
        initView()
        val productDb = getProductDb()
        showElement(productDb)

        Log.d("getProductDb()", productDb.toString())

    }

    private fun showElement(product: ProductDB) {
        textViewTitle.text = getString(R.string.description, product.title)
        textViewDescription.text = product.description
        textViewPrice.text = getString(R.string.price, product.price)
        textViewDiscount.text = getString(R.string.discount, product.discountPercentage)
        textViewRating.text = getString(R.string.rating, product.rating)
        textViewStock.text = getString(R.string.in_stock, product.stock)
        textViewBrand.text = getString(R.string.brand, product.brand)
        textViewCategory.text = getString(R.string.category, product.category)
        setupRecyclerView(product)
    }

    private fun setupRecyclerView(product: ProductDB) {
        val rvProduct = findViewById<RecyclerView>(R.id.recyclerViewImages)
        installRv(rvProduct, product.images.toString())
    }

    private fun installRv(rvProduct : RecyclerView, images: String) {
        with(rvProduct) {
            layoutManager = GridLayoutManager(context, 1)
            Log.d("installRv", images)
            imagesAdapter = ImagesAdapter(this.context, images.split(","))
            adapter = imagesAdapter
        }
    }


    private fun getProductDb(): ProductDB {
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val title = intent.getStringExtra(EXTRA_TITLE)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)
        val price = intent.getLongExtra(EXTRA_PRICE, 0)
        val discountPercentage = intent.getDoubleExtra(EXTRA_DISCOUNT_PERCENTAGE, 0.0)
        val rating = intent.getDoubleExtra(EXTRA_RATING, 0.0)
        val stock = intent.getLongExtra(EXTRA_STOCK, 0)
        val brand = intent.getStringExtra(EXTRA_BRAND)
        val category = intent.getStringExtra(EXTRA_CATEGORY)
        val thumbnail = intent.getStringExtra(EXTRA_THUMBNAIL)
        val images = intent.getStringExtra(EXTRA_IMAGES)
        val productDb = ProductDB(
            id = id,
            title = title,
            description = description,
            price = price,
            discountPercentage = discountPercentage,
            rating = rating,
            stock = stock,
            brand = brand,
            category = category,
            thumbnail = thumbnail,
            images = images
        )
        return productDb
    }

    companion object {
        private const val EXTRA_ID = "extra_id"
        private const val EXTRA_TITLE = "extra_title"
        private const val EXTRA_DESCRIPTION = "extra_description"
        private const val EXTRA_PRICE = "extra_price"
        private const val EXTRA_DISCOUNT_PERCENTAGE = "extra_discount_percentage"
        private const val EXTRA_RATING = "extra_rating"
        private const val EXTRA_STOCK = "extra_stock"
        private const val EXTRA_BRAND = "extra_brand"
        private const val EXTRA_CATEGORY = "extra_category"
        private const val EXTRA_THUMBNAIL = "extra_thumbnail"
        private const val EXTRA_IMAGES = "extra_images"

        fun newIntent(context: Context, product: ProductDB): Intent {
            val intent = Intent(context, ProductElementActivity::class.java)
            intent.putExtra(EXTRA_ID, product.id)
            intent.putExtra(EXTRA_TITLE, product.title)
            intent.putExtra(EXTRA_DESCRIPTION, product.description)
            intent.putExtra(EXTRA_PRICE, product.price)
            intent.putExtra(EXTRA_DISCOUNT_PERCENTAGE, product.discountPercentage)
            intent.putExtra(EXTRA_RATING, product.rating)
            intent.putExtra(EXTRA_STOCK, product.stock)
            intent.putExtra(EXTRA_BRAND, product.brand)
            intent.putExtra(EXTRA_CATEGORY, product.category)
            intent.putExtra(EXTRA_THUMBNAIL, product.thumbnail)
            intent.putExtra(EXTRA_IMAGES, product.images)
            return intent
        }
    }

}