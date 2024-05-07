package com.uteev.vkshop.presentation

import android.app.Application
import com.uteev.vkshop.di.DaggerApplicationComponent
import dagger.android.DaggerApplication

class ProductApp : Application() {
     val component by lazy {
         DaggerApplicationComponent.factory().create(this)
     }
}