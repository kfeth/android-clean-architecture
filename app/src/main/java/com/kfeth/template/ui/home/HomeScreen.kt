package com.kfeth.template.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.LaunchedEffect
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
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        uiState = uiState,
        onRefresh = viewModel::onRefresh,
        onErrorConsumed = viewModel::onErrorConsumed,
        onClickListItem = onClickListItem
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onRefresh: () -> Unit,
    onErrorConsumed: () -> Unit,
    onClickListItem: (String) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopBar() }
    ) { padding ->

        if (uiState.isError) {
            val errorMessage = stringResource(id = R.string.generic_error)
            val okText = stringResource(id = R.string.ok_button_text)

            LaunchedEffect(scaffoldState.snackbarHostState) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = errorMessage,
                    actionLabel = okText
                )
                onErrorConsumed()
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isRefreshing),
            onRefresh = onRefresh,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ArticleList(
                newsState = uiState.newsState,
                onClickListItem = onClickListItem
            )
        }
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
    ) {
        CircularProgressIndicator(color = Color.LightGray)
    }
}

@Composable
fun ArticleList(
    newsState: NewsUiState,
    onClickListItem: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        when (newsState) {
            NewsUiState.Error -> {
                item { ErrorLabel(R.string.generic_error) }
            }
            NewsUiState.Loading -> {
                item { LoadingIndicator() }
            }
            is NewsUiState.Success -> {
                items(newsState.articles) { article ->
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
            .background(Color.LightGray)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClickListItem(article.url) }
    ) {
        NetworkImage(
            imageUrl = article.imageUrl,
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

@Composable
fun ErrorLabel(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = title),
        modifier = modifier
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        Surface {
            HomeScreen(
                uiState = HomeUiState(
                    newsState = NewsUiState.Success(mockArticles),
                    isError = false,
                    isRefreshing = false
                ),
                onErrorConsumed = {},
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
