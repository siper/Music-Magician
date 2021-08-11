package ru.stersh.musicmagician.entity.api.itunes.album

import com.google.gson.annotations.SerializedName

data class ItunesAlbumResult(

        @SerializedName("resultCount") val resultCount: Int,
        @SerializedName("results") val results: List<AlbumSearchEntity>
)