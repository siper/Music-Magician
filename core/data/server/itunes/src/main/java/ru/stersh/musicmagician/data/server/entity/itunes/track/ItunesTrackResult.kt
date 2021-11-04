package ru.stersh.musicmagician.data.server.entity.itunes.track

import com.squareup.moshi.Json

data class ItunesTrackResult(
    @Json(name = "resultCount") val resultCount: Int,
    @Json(name = "results") val results: List<TrackSearchEntity>
)