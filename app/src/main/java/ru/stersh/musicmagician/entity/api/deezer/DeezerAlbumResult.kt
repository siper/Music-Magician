package ru.stersh.musicmagician.entity.api.deezer

import com.google.gson.annotations.SerializedName

class DeezerAlbumResult(
        @SerializedName("data") val data: List<AlbumItem>,
        @SerializedName("total") val total: Int,
        @SerializedName("next") val next: String
)