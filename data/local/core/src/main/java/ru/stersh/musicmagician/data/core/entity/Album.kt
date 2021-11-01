package ru.stersh.musicmagician.data.core.entity

import android.net.Uri

data class Album(
    val id: Long,
    val title: String,
    val artist: String,
    val albumArt: Uri,
    val year: String
)