package ru.stersh.musicmagician.data.server.deezer.entity

import com.squareup.moshi.Json

class DeezerTrackResult(
    @Json(name = "data") val data: List<TrackItem>,
    @Json(name = "total") val total: Int,
    @Json(name = "next") val next: String
)
