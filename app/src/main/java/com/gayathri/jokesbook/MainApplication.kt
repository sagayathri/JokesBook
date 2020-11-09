package com.gayathri.jokesbook

import android.app.Activity
import android.content.Context
import com.gayathri.jokesbook.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class MainApplication : DaggerApplication() {

    private val appComponent = DaggerAppComponent
        .builder()
        .application(this)
        .build()

    lateinit var context: Context

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent as AndroidInjector<out DaggerApplication>
    }
}