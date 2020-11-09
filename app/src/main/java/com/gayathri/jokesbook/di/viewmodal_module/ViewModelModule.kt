package com.gayathri.jokesbook.di.viewmodal_module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gayathri.jokesbook.di.key.ViewModelKey
import com.gayathri.jokesbook.ui.viewmodel.JokesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(JokesViewModel::class)
    abstract fun bindDaggerViewModel(daggerViewModel: JokesViewModel): ViewModel
}