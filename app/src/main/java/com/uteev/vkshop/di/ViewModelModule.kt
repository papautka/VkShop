package com.uteev.vkshop.di

import androidx.lifecycle.ViewModel
import com.uteev.vkshop.presentation.ProductViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    fun bindProductViewModel(viewModel : ProductViewModel) :ViewModel
}