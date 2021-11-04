package ru.stersh.musicmagician.data.server.deezer.entity

import com.squareup.moshi.Json

class TrackItem(
    @Json(name = "id") val id: Int,
    @Json(name = "readable") val readable: Boolean,
    @Json(name = "title") val title: String,
    @Json(name = "title_short") val titleShort: String,
    @Json(name = "title_version") val titleVersion: String,
    @Json(name = "link") val link: String,
    @Json(name = "duration") val duration: Int,
    @Json(name = "rank") val rank: Int,
    @Json(name = "explicit_lyrics") val explicitLyrics: Boolean,
    @Json(name = "preview") val preview: String,
    @Json(name = "artist") val artist: Artist,
    @Json(name = "album") val album: Album,
    @Json(name = "type") val type: String
)
