package ru.stersh.musicmagician.entity.api.deezer

import com.google.gson.annotations.SerializedName

class TrackItem(
        @SerializedName("id") val id: Int,
        @SerializedName("readable") val readable: Boolean,
        @SerializedName("title") val title: String,
        @SerializedName("title_short") val titleShort: String,
        @SerializedName("title_version") val titleVersion: String,
        @SerializedName("link") val link: String,
        @SerializedName("duration") val duration: Int,
        @SerializedName("rank") val rank: Int,
        @SerializedName("explicit_lyrics") val explicitLyrics: Boolean,
        @SerializedName("preview") val preview: String,
        @SerializedName("artist") val artist: Artist,
        @SerializedName("album") val album: Album,
        @SerializedName("type") val type: String
)
