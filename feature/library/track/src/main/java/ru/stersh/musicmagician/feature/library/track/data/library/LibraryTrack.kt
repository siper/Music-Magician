package ru.stersh.musicmagician.feature.library.track.data.library

import android.net.Uri

data class LibraryTrack(
    val id: Long = 0,
    val title: String,
    val dateAdded: Long = 0,
    val artist: String,
    val album: String,
    val uri: Uri,
    val albumArtUri: Uri?
)