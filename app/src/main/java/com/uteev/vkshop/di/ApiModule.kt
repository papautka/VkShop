package com.uteev.vkshop.di

import android.app.Application
import com.uteev.vkshop.data.api.ApiFactory
import com.uteev.vkshop.data.database.AppDatabase
import dagger.Module
import dagger.Provides

@Module
interface ApiModule {
    companion object {
        @Provides
        fun provideApiModule() : ApiFactory {
            return ApiFactory
        }
    }
}

