package com.uteev.vkshop.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uteev.vkshop.domain.pojo.ProductDB

@Database(entities = [ProductDB::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var db : AppDatabase? = null
        private const val DB_NAME = "productsDB"
        private val LOCK = Any()
        fun getInstance(context : Context) : AppDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it }
                val instance = Room.databaseBuilder(
                    context, AppDatabase::class.java, DB_NAME)
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun coinPriceInfoDao() : ProductInfoDao
}