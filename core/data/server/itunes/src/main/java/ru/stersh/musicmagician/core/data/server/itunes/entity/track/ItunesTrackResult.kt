package ru.stersh.musicmagician.core.data.server.itunes.entity.track

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItunesTrackResult(
    @Json(name = "resultCount") val resultCount: Int,
    @Json(name = "results") val results: List<TrackSearchEntity>
)