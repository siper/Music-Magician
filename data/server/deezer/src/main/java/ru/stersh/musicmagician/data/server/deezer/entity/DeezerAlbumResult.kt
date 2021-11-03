package ru.stersh.musicmagician.data.server.deezer.entity

import com.squareup.moshi.Json

class DeezerAlbumResult(
    @Json(name = "data") val data: List<AlbumItem>,
    @Json(name = "total") val total: Int,
    @Json(name = "next") val next: String
)