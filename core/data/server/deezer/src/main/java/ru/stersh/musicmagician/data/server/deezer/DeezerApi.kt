package ru.stersh.musicmagician.data.server.deezer

import retrofit2.http.GET
import retrofit2.http.Query
import ru.stersh.musicmagician.data.server.deezer.entity.DeezerTrackResult


interface DeezerApi {
    @GET("search")
    suspend fun searchTrackTags(@Query("q") query: String): DeezerTrackResult

    @GET("search")
    suspend fun searchAlbumTags(@Query("q") query: String): DeezerTrackResult
}