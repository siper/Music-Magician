package ru.stersh.musicmagician.data.core.internal.entity

import android.net.Uri

data class Track(
    val id: Long,
    val title: String,
    val dateAdded: Long,
    val dateModified: Long,
    val artist: String,
    val album: String,
    val uri: Uri,
    val albumId: Long,
    val duration: Long
)