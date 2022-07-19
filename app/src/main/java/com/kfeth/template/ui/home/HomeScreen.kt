package com.kfeth.template.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kfeth.template.R
import com.kfeth.template.data.Article
import com.kfeth.template.ui.components.NetworkImage
import com.kfeth.template.ui.theme.AppTheme
import com.kfeth.template.util.mockArticles

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClickListItem: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    HomeScreen(
        state = state,
        onRefresh = viewModel::onRefresh,
        onClickListItem = onClickListItem
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onRefresh: () -> Unit,
    onClickListItem: (String) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopBar() }
    ) { padding ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.isRefreshing),
            onRefresh = onRefresh,
            modifier = Modifier.padding(padding)
        ) {
            ArticleList(
                uiState = state.latestNews,
                onClickListItem = onClickListItem
            )
        }
        // todo error handle
//        var localError by rememberSaveable(state.error) { mutableStateOf(state.error) }
//        // If the state contains an error -> show snackBar w/retry & avoid repeat/spamming messages
//        if (localError != null) {
//            val message = stringResource(R.string.generic_error, localError?.message ?: "")
//            val retryLabel = stringResource(R.string.retry)
//
//            LaunchedEffect(scaffoldState.snackbarHostState) {
//                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
//                    message = message,
//                    actionLabel = retryLabel
//                )
//                if (snackBarResult == SnackbarResult.ActionPerformed) {
//                    onRefresh()
//                }
//                localError = null
//            }
//        }
    }
}

@Composable
fun HomeTopBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) }
    )
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CircularProgressIndicator(color = Color.LightGray)
    }
}

@Composable
fun ArticleList(
    uiState: NewsUiState,
    onClickListItem: (String) -> Unit
) {
    LazyColumn {
        when (uiState) {
            NewsUiState.Error -> {
                // todo error handle
            }
            NewsUiState.Loading -> {
                item { LoadingIndicator() }
            }
            is NewsUiState.Success -> {
                items(uiState.articles) { article ->
                    ArticleListItem(article = article, onClickListItem = onClickListItem)
                }
            }
        }
    }
}

@Composable
fun ArticleListItem(
    article: Article,
    onClickListItem: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray.copy(alpha = 0.2f))
            .clip(MaterialTheme.shapes.medium)
        // todo fix click & image
//            .clickable { onClickListItem(article.url) }
    ) {
        NetworkImage(
            imageUrl = "",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        ArticleTitleText(
            text = article.title,
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        )
    }
}

@Composable
fun ArticleTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = typography.caption,
        color = Color.White,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.6f))
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        Surface {
            HomeScreen(
                state = HomeUiState(
                    latestNews = NewsUiState.Success(mockArticles),
                    isError = false,
                    isRefreshing = false
                ),
                onRefresh = {},
                onClickListItem = {}
            )
        }
    }
}

@Preview
@Composable
fun ArticleListItemPreview() {
    Surface {
        ArticleListItem(
            article = mockArticles.first(),
            onClickListItem = {}
        )
    }
}
