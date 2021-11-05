package ru.stersh.musicmagician.core.data.server.cajunlyrics

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CajunlyricsApi {
    @GET("LyricDirectSearch.php")
    suspend fun searchLyrics(@Query("title") title: String, @Query("artist") artist: String): ResponseBody
}