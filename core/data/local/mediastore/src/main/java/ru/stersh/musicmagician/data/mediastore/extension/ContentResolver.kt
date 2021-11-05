package ru.stersh.musicmagician.data.mediastore.extension

import android.content.ContentResolver
import android.net.Uri

fun ContentResolver.isUriExists(uri: Uri) = runCatching {
    openInputStream(uri).use {  }
}.isSuccess