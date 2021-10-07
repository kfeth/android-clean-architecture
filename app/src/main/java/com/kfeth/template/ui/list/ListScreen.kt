package com.kfeth.template.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.kfeth.template.R
import com.kfeth.template.data.Article
import com.kfeth.template.features.breakingnews.BreakingNewsViewModel
import com.kfeth.template.ui.theme.AppTheme
import com.kfeth.template.util.UiState
import com.kfeth.template.util.mockArticles
import timber.log.Timber

@Composable
fun ListScreen(
    viewModel: BreakingNewsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    ListScreen(state)
}

@Composable
fun ListScreen(
    state: UiState<List<Article>>
) {
    Timber.d("State: $state : ${state.data?.size}")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        }
    ) {
        ArticleList(articles = state.data.orEmpty())

        if (state is UiState.Loading) {
            LoadingIndicator()
        }
    }
}

@Composable
fun ArticleList(
    articles: List<Article>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = articles) {
            ArticleListItem(article = it)
        }
    }
}

@Composable
fun ArticleListItem(
    article: Article
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .clickable(onClick = { /* Todo */ })
    ) {
        NetworkImage(
            imageUrl = article.imageUrl,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        GradientText(
            text = article.title,
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        )
    }
}

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    placeholderResId: Int = R.drawable.placeholder,
) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(enable = true)
                placeholder(placeholderResId)
                error(placeholderResId)
                fallback(placeholderResId)
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
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

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun ListScreenPreview() {
    AppTheme {
        Surface {
            ListScreen(
                state = UiState.Success(data = mockArticles())
            )
        }
    }
}

@Preview
@Composable
fun ArticleListItemPreview() {
    Surface {
        ArticleListItem(
            article = mockArticles().first()
        )
    }
}

//@Composable
//fun ArticleListItem(
//    article: Article,
//    modifier: Modifier = Modifier
//) {
//    val typography = MaterialTheme.typography
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .clip(shape = MaterialTheme.shapes.medium)
//    ) {
//        Image(
//            painter = painterResource(R.drawable.ic_launcher_background),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .heightIn(max = 100.dp)
//                .fillMaxWidth()
//        )
//        Spacer(Modifier.height(16.dp))
//        Text(
//            text = article.title,
//            style = typography.h6,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//        Text(
//            text = article.author.orEmpty(),
//            style = typography.subtitle2,
//            modifier = Modifier.padding(bottom = 4.dp)
//        )
//    }
//}
