package com.gayathri.jokesbook.di.module

import com.gayathri.jokesbook.ui.view.InitialFragment
import com.gayathri.jokesbook.ui.view.PunchLineFragment
import com.gayathri.jokesbook.ui.view.TypesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun initialFragment(): InitialFragment

    @ContributesAndroidInjector
    abstract fun typesFragment(): TypesFragment

    @ContributesAndroidInjector
    abstract fun punchLineFragment(): PunchLineFragment
}