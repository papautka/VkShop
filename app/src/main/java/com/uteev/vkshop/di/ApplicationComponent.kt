package com.uteev.vkshop.di

import android.app.Application
import com.uteev.vkshop.presentation.ProductListActivity
import dagger.Binds
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]

)
interface ApplicationComponent {
    fun inject(activity : ProductListActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent
    }
}