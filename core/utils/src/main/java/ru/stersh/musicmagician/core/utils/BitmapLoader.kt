package ru.stersh.musicmagician.core.utils

import android.content.Context
import android.graphics.Bitmap
import coil.ImageLoader
import coil.request.ImageRequest

class BitmapLoader(private val context: Context) {
    private val imageLoader = ImageLoader(context)

    suspend fun load(url: String): Bitmap {
        val request = ImageRequest.Builder(context)
            .data(url)
            .build()
        return imageLoader.execute(request).drawable!!.toBitmap()
    }
}