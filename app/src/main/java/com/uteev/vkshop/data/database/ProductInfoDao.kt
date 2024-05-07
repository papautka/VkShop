package com.uteev.vkshop.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.uteev.vkshop.domain.pojo.ProductDB

@Dao
interface ProductInfoDao {
    @Query("SELECT * FROM productsDB ORDER BY id")
    fun getProductList(): LiveData<List<ProductDB>>

    @Query("SELECT * FROM productsDB WHERE id = :id LIMIT 1")
    fun getProduct(id: Int): LiveData<ProductDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<ProductDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductDB)

    @Query("DELETE FROM productsDB")
    fun deleteAll()

    @Transaction
    fun replaceProductList(newProductList: List<ProductDB>) {
        deleteAll() // Удаление старых записей
        insertPriceList(newProductList) // Вставка новых записей
    }
}
