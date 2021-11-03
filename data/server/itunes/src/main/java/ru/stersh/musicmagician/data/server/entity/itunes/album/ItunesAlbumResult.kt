package ru.stersh.musicmagician.data.server.entity.itunes.album

import com.squareup.moshi.Json

data class ItunesAlbumResult(
    @Json(name = "resultCount") val resultCount: Int,
    @Json(name = "results") val results: List<AlbumSearchEntity>
)