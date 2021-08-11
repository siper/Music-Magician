package ru.stersh.musicmagician.entity.api.deezer

import com.google.gson.annotations.SerializedName

class DeezerTrackResult(
        @SerializedName("data") val data: List<TrackItem>,
        @SerializedName("total") val total: Int,
        @SerializedName("next") val next: String
)
