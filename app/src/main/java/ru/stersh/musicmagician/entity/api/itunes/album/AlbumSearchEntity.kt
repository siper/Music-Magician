package ru.stersh.musicmagician.entity.api.itunes.album

import com.google.gson.annotations.SerializedName

data class AlbumSearchEntity(

        @SerializedName("wrapperType") val wrapperType: String,
        @SerializedName("collectionType") val collectionType: String,
        @SerializedName("artistId") val artistId: Int,
        @SerializedName("collectionId") val collectionId: Int,
        @SerializedName("amgArtistId") val amgArtistId: Int,
        @SerializedName("artistName") val artistName: String,
        @SerializedName("collectionName") val collectionName: String,
        @SerializedName("collectionCensoredName") val collectionCensoredName: String,
        @SerializedName("artistViewUrl") val artistViewUrl: String,
        @SerializedName("collectionViewUrl") val collectionViewUrl: String,
        @SerializedName("artworkUrl60") val artworkUrl60: String,
        @SerializedName("artworkUrl100") val artworkUrl100: String,
        @SerializedName("collectionPrice") val collectionPrice: Double,
        @SerializedName("collectionExplicitness") val collectionExplicitness: String,
        @SerializedName("contentAdvisoryRating") val contentAdvisoryRating: String,
        @SerializedName("trackCount") val trackCount: Int,
        @SerializedName("copyright") val copyright: String,
        @SerializedName("country") val country: String,
        @SerializedName("currency") val currency: String,
        @SerializedName("releaseDate") val releaseDate: String,
        @SerializedName("primaryGenreName") val primaryGenreName: String
)