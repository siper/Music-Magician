package ru.stersh.musicmagician.core.data.server.deezer.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DeezerTrackResult(
    @Json(name = "data") val data: List<TrackItem>,
    @Json(name = "total") val total: Int,
    @Json(name = "next") val next: String?
)
