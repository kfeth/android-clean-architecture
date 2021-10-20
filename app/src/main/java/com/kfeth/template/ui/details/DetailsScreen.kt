package com.kfeth.template.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kfeth.template.R
import com.kfeth.template.data.Article
import com.kfeth.template.ui.components.NetworkImage
import com.kfeth.template.ui.theme.AppTheme
import com.kfeth.template.util.mockArticles

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    articleId: String,
    onNavigateUp: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    viewModel.loadArticle(articleId)

    DetailsScreen(
        state = state,
        onNavigateUp = onNavigateUp
    )
}

@Composable
fun DetailsScreen(
    state: DetailsUiState,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        state.article?.let {
            ArticleDetails(article = state.article)
        }
    }
}

@Composable
fun ArticleDetails(
    article: Article
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        NetworkImage(
            imageUrl = article.imageUrl,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = article.title,
            style = typography.h6,
            modifier = Modifier.padding(8.dp)
        )

        if (!article.author.isNullOrEmpty()) {
            Text(
                text = article.author,
                style = typography.subtitle2,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            Spacer(Modifier.height(8.dp))
        }
        Text(
            text = article.content.orEmpty(),
            style = typography.body1,
            modifier = Modifier.padding(8.dp)
        )
        ReadMoreButton(
            url = article.url,
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}

@Composable
fun ReadMoreButton(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }

    TextButton(
        onClick = { context.startActivity(intent) },
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.read_more),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    AppTheme {
        Surface {
            DetailsScreen(
                state = DetailsUiState(article = mockArticles.first()),
                onNavigateUp = { }
            )
        }
    }
}
