package com.kfeth.template.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.template.data.ArticleEntity
import com.kfeth.template.data.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState(loading = true))
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadArticle(articleId: String) {
        viewModelScope.launch {
            repository.getArticle(articleId).collect { article ->
                _uiState.update {
                    it.copy(
                        article = article,
                        loading = false
                    )
                }
            }
        }
    }
}

data class DetailsUiState(
    val loading: Boolean = false,
    val article: ArticleEntity? = null
)
