package ru.stersh.musicmagician.data.server.lololyrics

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface LololyricsApi {
    @GET("getLyric")
    suspend fun searchLyrics(@Query("track") title: String, @Query("artist") artist: String): ResponseBody
}