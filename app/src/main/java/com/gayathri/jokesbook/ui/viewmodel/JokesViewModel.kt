package com.gayathri.jokesbook.ui.viewmodel

import androidx.lifecycle.*
import com.gayathri.jokesbook.data.model.Jokes
import com.gayathri.jokesbook.repository.JokesRepo
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokesViewModel @Inject constructor(
    private val repo: JokesRepo
): ViewModel() {
    val jokesLiveData = MutableLiveData<String>()

    fun getJokes(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            jokesLiveData.postValue(repo.getJokes(type))
        }
    }
}