package ru.stersh.musicmagician.data.server.core.entity

data class TrackTag(
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val albumArtUrl: String = "",
    val genre: String = "",
    val year: Int = 0,
    val number: Int = 0
)