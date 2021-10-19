package com.kfeth.template.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.template.data.Article
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
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            repository.getBreakingNews().collect { resource ->
                _uiState.update {
                    it.copy(
                        articles = resource.data.orEmpty(),
                        loading = resource.isLoading,
                        error = resource.error
                    )
                }
            }
        }
    }
}

data class HomeUiState(
    val articles: List<Article> = emptyList(),
    val loading: Boolean = false,
    val error: Throwable? = null
)
