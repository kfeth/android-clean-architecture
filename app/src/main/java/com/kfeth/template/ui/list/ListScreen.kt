package com.kfeth.template.ui.list

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.kfeth.template.data.Article
import com.kfeth.template.features.breakingnews.BreakingNewsViewModel
import com.kfeth.template.util.Resource
import timber.log.Timber

@Composable
fun ListScreen(
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {
    val initial = Resource.Loading<List<Article>>(emptyList())
    val state by viewModel.resource.observeAsState(initial)
    ListScreen(state)
}

@Composable
fun ListScreen(state: Resource<List<Article>>) {
    Timber.d("State: $state : ${state.data?.size}")
    Text(text = "$state")
}
