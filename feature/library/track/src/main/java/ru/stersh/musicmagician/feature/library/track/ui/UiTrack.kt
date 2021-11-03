package ru.stersh.musicmagician.feature.library.track.ui

import android.net.Uri

sealed class UiItem {
    data class UiTrack(
        val id: Long = 0,
        val title: String,
        val dateAdded: Long = 0,
        val artist: String,
        val album: String,
        val uri: Uri,
        val albumArtUri: Uri?
    ) : UiItem()
    object Progress : UiItem()
}

