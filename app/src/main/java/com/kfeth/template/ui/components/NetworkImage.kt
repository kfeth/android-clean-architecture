package com.kfeth.template.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.kfeth.template.R

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box {
        Image(
            painterResource(R.drawable.ic_img_placeholder),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center)
                .alpha(0.5f)
        )
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = { crossfade(true) }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun NetworkImagePreview() {
    Surface {
        NetworkImage(
            imageUrl = "",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
    }
}
