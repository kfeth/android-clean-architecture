package com.kfeth.template.features.details

import androidx.lifecycle.*
import com.kfeth.template.data.Article
import com.kfeth.template.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    repository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val articleUrl = savedStateHandle.getLiveData<String>("articleUrl")

    val article: LiveData<Article> = articleUrl.switchMap {
        repository.getArticle(it).asLiveData(viewModelScope.coroutineContext)
    }
}