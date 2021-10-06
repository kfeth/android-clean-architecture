package com.kfeth.template.features.breakingnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.template.data.Article
import com.kfeth.template.data.NewsRepository
import com.kfeth.template.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading(emptyList()))
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            repository.getBreakingNews().collect {
                _uiState.value = it
            }
        }
    }
}
