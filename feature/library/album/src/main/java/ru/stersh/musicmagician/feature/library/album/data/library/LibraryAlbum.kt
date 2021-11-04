package ru.stersh.musicmagician.feature.library.album.data.library

import android.net.Uri

data class LibraryAlbum(
    val id: Long,
    val title: String,
    val artist: String,
    val albumArtUri: Uri?,
    val year: Int?
)