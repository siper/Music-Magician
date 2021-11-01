package ru.stersh.musicmagician.data.core.entity

import android.net.Uri

data class Track(
    val id: Long = 0,
    val title: String,
    val dateAdded: Long = 0,
    val dateModified: Long = 0,
    val artist: String,
    val album: String,
    val trackNumber: String,
    val year: String,
    val uri: Uri,
    val albumId: Long,
    val duration: Long
)