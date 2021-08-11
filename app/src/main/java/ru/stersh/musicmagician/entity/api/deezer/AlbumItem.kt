package ru.stersh.musicmagician.entity.api.deezer

import com.google.gson.annotations.SerializedName

class AlbumItem(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("link") val link: String,
        @SerializedName("cover") val cover: String,
        @SerializedName("cover_small") val coverSmall: String,
        @SerializedName("cover_medium") val coverMedium: String,
        @SerializedName("cover_big") val coverBig: String,
        @SerializedName("cover_xl") val coverXl: String,
        @SerializedName("genre_id") val genreId: Int,
        @SerializedName("nb_tracks") val nbTracks: Int,
        @SerializedName("record_type") val recordType: String,
        @SerializedName("tracklist") val tracklist: String,
        @SerializedName("explicit_lyrics") val explicitLyrics: Boolean,
        @SerializedName("artist") val artist: Artist,
        @SerializedName("type") val type: String
)
