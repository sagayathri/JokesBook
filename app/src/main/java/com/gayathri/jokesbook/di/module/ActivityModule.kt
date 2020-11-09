package com.gayathri.jokesbook.di.module

import com.gayathri.jokesbook.di.annotation.ActivityScope
import com.gayathri.jokesbook.ui.view.InitialFragment
import com.gayathri.jokesbook.ui.view.MainActivity
import com.gayathri.jokesbook.ui.view.PunchLineFragment
import com.gayathri.jokesbook.ui.view.TypesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScope
    abstract fun contributeMainActivity(): MainActivity
}

