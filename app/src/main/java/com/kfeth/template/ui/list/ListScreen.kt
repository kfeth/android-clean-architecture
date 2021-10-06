package com.kfeth.template.ui.list

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.kfeth.template.data.Article
import com.kfeth.template.features.breakingnews.BreakingNewsViewModel
import com.kfeth.template.util.UiState
import timber.log.Timber

@Composable
fun ListScreen(
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    ListScreen(state)
}

@Composable
fun ListScreen(state: UiState<List<Article>>) {
    Timber.d("State: $state : ${state.data?.size}")
    Text(text = "$state")
}
