package com.gayathri.jokesbook.di.component

import com.gayathri.jokesbook.MainApplication
import com.gayathri.jokesbook.di.module.*
import com.gayathri.jokesbook.di.viewmodal_module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AndroidInjectionModule::class),
        (AndroidSupportInjectionModule::class),
        (ActivityModule::class),
        (FragmentModule::class),
        (ViewModule::class),
        (ViewModelModule::class),
        (AppModule::class),
        (NetworkModule::class)
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    //override fun inject(instance: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MainApplication): Builder

        fun build(): AppComponent
    }

   // fun provideModelRepo(): JokesRepo
}