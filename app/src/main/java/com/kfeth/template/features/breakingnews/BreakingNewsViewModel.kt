package com.kfeth.template.features.breakingnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.template.data.Article
import com.kfeth.template.data.NewsRepository
import com.kfeth.template.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    repository: NewsRepository
) : ViewModel() {

    private val _resource: MutableLiveData<Resource<List<Article>>> = MutableLiveData()
    val resource: LiveData<Resource<List<Article>>> = _resource

    init {
        viewModelScope.launch {
            repository.getBreakingNews().collect {
                _resource.value = it
            }
        }
    }
}
