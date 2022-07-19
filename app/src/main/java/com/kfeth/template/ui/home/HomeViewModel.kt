package com.kfeth.template.ui.home

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfeth.template.data.ArticleRes
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

data class UiState(
    val topNews: NewsUiState,
    val isRefreshing: Boolean,
    val isError: Boolean
)

@Immutable
sealed interface NewsUiState {
    data class Success(val news: List<ArticleRes>) : NewsUiState
    object Error : NewsUiState
    object Loading : NewsUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val allNews: Flow<Result<List<ArticleRes>>> =
        repository.getNewsStream().asResult()

    private val isRefreshing = MutableStateFlow(false)
    private val isError = MutableStateFlow(false)

    val uiState: StateFlow<UiState> = combine(
        allNews,
        isRefreshing,
        isError
    ) { newsResult, refreshing, errorOccurred ->

        val topNews: NewsUiState = when (newsResult) {
            is Result.Success -> NewsUiState.Success(newsResult.data)
            is Result.Loading -> NewsUiState.Loading
            is Result.Error -> NewsUiState.Error
        }
        UiState(
            topNews,
            refreshing,
            errorOccurred
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiState(
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
