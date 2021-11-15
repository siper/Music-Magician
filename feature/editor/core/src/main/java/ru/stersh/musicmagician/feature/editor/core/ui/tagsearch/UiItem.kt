package ru.stersh.musicmagician.feature.editor.core.ui.tagsearch

import androidx.annotation.StringRes

sealed class UiItem {
    data class Track(
        val title: String,
        val artist: String,
        val album: String,
        val albumArtUrl: String,
        val genre: String?,
        val year: String?,
        val trackNumber: String?
    ) : UiItem()

    data class Album(
        val album: String,
        val artist: String,
        val albumArtUrl: String,
        val year: Int
    ) : UiItem()

    data class Lyric(
        val lyrics: String
    ) : UiItem()

    data class Header(@StringRes val titleResId: Int) : UiItem()

    object Progress : UiItem()
}