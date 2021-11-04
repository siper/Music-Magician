package ru.stersh.musicmagician.feature.library.album.ui.entity

import android.net.Uri

sealed class UiItem {
    data class Album(
        val id: Long,
        val title: String,
        val artist: String,
        val albumArtUri: Uri?
    ) : UiItem()

    object Progress : UiItem()
}