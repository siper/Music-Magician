package ru.stersh.musicmagician.entity.tag

import java.util.*

data class AlbumTag(
        val album: String,
        val artist: String,
        val albumart: String,
        val year: Int = 0,
        override val provider: String,
        override val priority: Int = 0
) : Tag {
    fun calculatePriority(query: String): AlbumTag {
        var p = 0
        val lQuery = query.lowercase(Locale.getDefault())
        if (album.isNotBlank() && album.isNotEmpty()) {
            p++
            if (query.contains(album)) p += 3
            if (lQuery.contains(album)) p += 3
        }
        Stopwords.ALBUM_TITLE.forEach {
            if (album.contains(it)) p--
        }
        if (artist.isNotBlank() && artist.isNotEmpty()) {
            p++
            if (query.contains(artist)) p += 3
            if (lQuery.contains(artist)) p += 3
        }
        if (albumart.isNotBlank() && albumart.isNotEmpty()) {
            p++
        }
        if (year > 0) {
            p++
        }
        return this.copy(priority = p)
    }
}