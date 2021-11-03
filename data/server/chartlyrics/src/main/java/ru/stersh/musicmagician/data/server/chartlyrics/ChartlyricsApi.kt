package ru.stersh.musicmagician.data.server.chartlyrics

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ChartlyricsApi {
    @GET("apiv1.asmx/SearchLyricDirect")
    suspend fun searchLyrics(@Query("song") title: String, @Query("artist") artist: String): ResponseBody
}