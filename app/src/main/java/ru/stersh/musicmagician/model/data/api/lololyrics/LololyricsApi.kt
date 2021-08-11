package ru.stersh.musicmagician.model.data.api.lololyrics

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface LololyricsApi {
    @GET("getLyric")
    fun searchLyrics(@Query("track") title: String, @Query("artist") artist: String): Single<ResponseBody>
}