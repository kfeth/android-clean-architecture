package com.kfeth.template.ui.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsScreen(
    articleId: String
) {
    Text(text = "Details: $articleId")
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
