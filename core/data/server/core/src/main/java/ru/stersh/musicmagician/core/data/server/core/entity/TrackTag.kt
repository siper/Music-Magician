package ru.stersh.musicmagician.core.data.server.core.entity

data class TrackTag(
    val title: String,
    val artist: String,
    val album: String,
    val albumArtUrl: String,
    val genre: String?,
    val year: String?,
    val number: String?
)