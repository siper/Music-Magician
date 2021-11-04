package ru.stersh.musicmagician.data.server.core.entity

data class AlbumTag(
    val album: String,
    val artist: String,
    val albumArtUrl: String,
    val year: Int = 0
)