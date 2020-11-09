package com.gayathri.jokesbook.di.module

import com.gayathri.jokesbook.di.viewmodal_module.ViewModelModule
import com.gayathri.jokesbook.ui.view.MainActivity
import com.gayathri.jokesbook.ui.view.TypesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeActivityAndroidInjector(): MainActivity
}