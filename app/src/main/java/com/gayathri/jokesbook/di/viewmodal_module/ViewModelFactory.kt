package com.gayathri.jokesbook.di.viewmodal_module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {
    private var viewModel: ViewModel? = null

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ViewModel::class.java) || ViewModel::class.java.isAssignableFrom(modelClass)) {
            viewModel?.let { return it as T }
            viewModel = viewModels[modelClass]?.get()

            return viewModel as T
        }
        throw ExceptionInInitializerError("not assignable class $modelClass")
    }
}