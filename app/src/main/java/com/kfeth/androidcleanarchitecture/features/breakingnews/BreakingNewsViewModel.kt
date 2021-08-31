package com.kfeth.androidcleanarchitecture.features.breakingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.androidcleanarchitecture.data.ArticleEntity
import com.kfeth.androidcleanarchitecture.data.NewsRepository
import com.kfeth.androidcleanarchitecture.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    repository: NewsRepository
) : ViewModel() {

    private val _resource: MutableLiveData<Resource<List<ArticleEntity>>> = MutableLiveData()
    val resource: LiveData<Resource<List<ArticleEntity>>> = _resource

    init {
        viewModelScope.launch {
            repository.getBreakingNews().collect {
                _resource.value = it
            }
        }
    }

    /* Example of using a loadTrigger for manual refreshes
    private val loadTrigger = MutableLiveData(Unit)

    val resource: LiveData<Resource<List<User>>> = loadTrigger.switchMap {
        repository.getUsers().asLiveData(viewModelScope.coroutineContext)
    }

    fun onManualRefresh() { loadTrigger.value = Unit }
     */
}