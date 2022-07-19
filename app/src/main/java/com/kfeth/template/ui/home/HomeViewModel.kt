package com.kfeth.template.ui.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.template.data.Article
import com.kfeth.template.data.NewsRepository
import com.kfeth.template.util.Result
import com.kfeth.template.util.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val latestNews: NewsUiState,
    val isRefreshing: Boolean,
    val isError: Boolean
)

@Immutable
sealed interface NewsUiState {
    data class Success(val articles: List<Article>) : NewsUiState
    object Error : NewsUiState
    object Loading : NewsUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val latestNews: Flow<Result<List<Article>>> =
        repository.getNewsStream().asResult()

    private val isRefreshing = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)

    val uiState: StateFlow<HomeUiState> = combine(
        latestNews,
        isRefreshing,
        isError
    ) { newsResult, refreshing, errorOccurred ->
        val latestNews: NewsUiState = when (newsResult) {
            is Result.Success -> NewsUiState.Success(newsResult.data)
            is Result.Loading -> NewsUiState.Loading
            is Result.Error -> NewsUiState.Error
        }
        HomeUiState(
            latestNews,
            refreshing,
            errorOccurred
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(
                NewsUiState.Loading,
                isRefreshing = false,
                isError = false
            )
        )

    fun onRefresh() {
        viewModelScope.launch {
            with(repository) {
                isRefreshing.emit(true)
                try {
                    refreshNews()
                } finally {
                    isRefreshing.emit(false)
                }
            }
        }
    }
}
