package ru.stersh.musicmagician.entity.api.itunes.track

import com.google.gson.annotations.SerializedName

data class ItunesTrackResult(

        @SerializedName("resultCount") val resultCount: Int,
        @SerializedName("results") val results: List<TrackSearchEntity>
)