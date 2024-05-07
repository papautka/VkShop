package com.uteev.vkshop.di

import android.app.Application
import com.uteev.vkshop.data.database.AppDatabase
import com.uteev.vkshop.data.database.ProductInfoDao
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    companion object {
        @Provides
        fun provideDataModule(
            application : Application
        ) : AppDatabase {
            return AppDatabase.getInstance(application)
        }
    }
}