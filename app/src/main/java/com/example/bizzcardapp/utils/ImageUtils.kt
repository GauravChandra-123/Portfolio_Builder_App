package com.example.bizzcardapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.scale

fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return try {
        val stream = context.contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(stream).also {
            stream?.close()
        }
    } catch (e: Exception) {
        null
    }
}

fun scaleBitmapToWidth(bmp: Bitmap, width: Int): Bitmap {
    val ratio = width.toFloat() / bmp.width
    val height = (bmp.height * ratio).toInt()
    return bmp.scale(width, height)
}
