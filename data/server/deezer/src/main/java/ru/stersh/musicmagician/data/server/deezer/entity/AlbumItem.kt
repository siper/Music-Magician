package ru.stersh.musicmagician.data.server.deezer.entity

import com.squareup.moshi.Json

class AlbumItem(
    @Json(name = "id") val id: Int,
    @Json(name = "title") val title: String,
    @Json(name = "link") val link: String,
    @Json(name = "cover") val cover: String,
    @Json(name = "cover_small") val coverSmall: String,
    @Json(name = "cover_medium") val coverMedium: String,
    @Json(name = "cover_big") val coverBig: String,
    @Json(name = "cover_xl") val coverXl: String,
    @Json(name = "genre_id") val genreId: Int,
    @Json(name = "nb_tracks") val nbTracks: Int,
    @Json(name = "record_type") val recordType: String,
    @Json(name = "tracklist") val tracklist: String,
    @Json(name = "explicit_lyrics") val explicitLyrics: Boolean,
    @Json(name = "artist") val artist: Artist,
    @Json(name = "type") val type: String
)
