package com.example.bizzcardapp.ui.components


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun ImagePreview(
    uri: Uri,
    size: Dp
) {
    Image(
        painter = rememberAsyncImagePainter(model = uri),
        contentDescription = null,
        modifier = Modifier
            .size(size)
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}
