package ru.stersh.musicmagician.feature.library.album

import android.net.Uri

sealed class UiItem {
    data class Album(
        val id: Long,
        val title: String,
        val artist: String,
        val albumArt: Uri,
        val year: String
    ) : UiItem()
    object Progress : UiItem()
}