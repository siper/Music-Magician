package ru.stersh.musicmagician.core.data.server.itunes.entity.album

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItunesAlbumResult(
    @Json(name = "resultCount") val resultCount: Int,
    @Json(name = "results") val results: List<AlbumSearchEntity>
)