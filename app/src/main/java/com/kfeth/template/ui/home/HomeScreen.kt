package com.kfeth.template.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kfeth.template.R
import com.kfeth.template.data.Article
import com.kfeth.template.ui.components.NetworkImage
import com.kfeth.template.ui.theme.AppTheme
import com.kfeth.template.util.mockArticles
import timber.log.Timber

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onArticleTap: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    HomeScreen(
        state = state,
        onSwipeRefresh = { viewModel.refreshData() },
        onArticleTap = onArticleTap
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onSwipeRefresh: () -> Unit,
    onArticleTap: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        }
    ) {
        Timber.d("State: loading=${state.loading}, articles=${state.articles.size}")

        SwipeRefresh(
            state = rememberSwipeRefreshState(state.loading),
            onRefresh = onSwipeRefresh,
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    backgroundColor = MaterialTheme.colors.primary
                )
            }
        ) {
            ArticleList(
                articles = state.articles,
                onArticleTap = onArticleTap
            )
        }
    }
}

@Composable
fun ArticleList(
    articles: List<Article>,
    onArticleTap: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = articles) {
            ArticleListItem(
                article = it,
                onArticleTap = onArticleTap
            )
        }
    }
}

@Composable
fun ArticleListItem(
    article: Article,
    onArticleTap: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable(onClick = { onArticleTap(article.url) })
    ) {
        NetworkImage(
            imageUrl = article.imageUrl,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        GradientText(
            text = article.title,
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        )
    }
}

@Composable
fun GradientText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = typography.h6,
        color = Color.White,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.2f),
                        Color.Black.copy(alpha = 0.8f)
                    )
                )
            )
            .padding(8.dp)
    )
}

@Preview
@Composable
fun ListScreenPreview() {
    AppTheme {
        Surface {
            HomeScreen(
                state = HomeUiState(articles = mockArticles()),
                onSwipeRefresh = { },
                onArticleTap = { }
            )
        }
    }
}

@Preview
@Composable
fun ArticleListItemPreview() {
    Surface {
        ArticleListItem(
            article = mockArticles().first(),
            onArticleTap = { }
        )
    }
}
