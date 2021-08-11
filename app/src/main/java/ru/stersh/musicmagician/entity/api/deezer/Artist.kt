package ru.stersh.musicmagician.entity.api.deezer

import com.google.gson.annotations.SerializedName

class Artist(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("link") val link: String,
        @SerializedName("picture") val picture: String,
        @SerializedName("picture_small") val pictureSmall: String,
        @SerializedName("picture_medium") val pictureMedium: String,
        @SerializedName("picture_big") val pictureBig: String,
        @SerializedName("picture_xl") val pictureXl: String,
        @SerializedName("tracklist") val tracklist: String,
        @SerializedName("type") val type: String
)