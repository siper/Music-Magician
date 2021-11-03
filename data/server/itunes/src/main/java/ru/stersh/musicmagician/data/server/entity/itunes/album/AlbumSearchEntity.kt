package ru.stersh.musicmagician.data.server.entity.itunes.album

import com.squareup.moshi.Json

data class AlbumSearchEntity(
    @Json(name = "wrapperType") val wrapperType: String,
    @Json(name = "collectionType") val collectionType: String,
    @Json(name = "artistId") val artistId: Int,
    @Json(name = "collectionId") val collectionId: Int,
    @Json(name = "amgArtistId") val amgArtistId: Int,
    @Json(name = "artistName") val artistName: String,
    @Json(name = "collectionName") val collectionName: String,
    @Json(name = "collectionCensoredName") val collectionCensoredName: String,
    @Json(name = "artistViewUrl") val artistViewUrl: String,
    @Json(name = "collectionViewUrl") val collectionViewUrl: String,
    @Json(name = "artworkUrl60") val artworkUrl60: String,
    @Json(name = "artworkUrl100") val artworkUrl100: String,
    @Json(name = "collectionPrice") val collectionPrice: Double,
    @Json(name = "collectionExplicitness") val collectionExplicitness: String,
    @Json(name = "contentAdvisoryRating") val contentAdvisoryRating: String,
    @Json(name = "trackCount") val trackCount: Int,
    @Json(name = "copyright") val copyright: String,
    @Json(name = "country") val country: String,
    @Json(name = "currency") val currency: String,
    @Json(name = "releaseDate") val releaseDate: String,
    @Json(name = "primaryGenreName") val primaryGenreName: String
)