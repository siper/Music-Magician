package ru.stersh.musicmagician.data.server.deezer.entity

import com.squareup.moshi.Json


class Artist(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "link") val link: String,
    @Json(name = "picture") val picture: String,
    @Json(name = "picture_small") val pictureSmall: String,
    @Json(name = "picture_medium") val pictureMedium: String,
    @Json(name = "picture_big") val pictureBig: String,
    @Json(name = "picture_xl") val pictureXl: String,
    @Json(name = "tracklist") val tracklist: String,
    @Json(name = "type") val type: String
)