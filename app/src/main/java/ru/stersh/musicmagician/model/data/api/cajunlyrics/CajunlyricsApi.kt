package ru.stersh.musicmagician.model.data.api.cajunlyrics

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface CajunlyricsApi {
    @GET("LyricDirectSearch.php")
    fun searchLyrics(@Query("title") title: String, @Query("artist") artist: String): Single<ResponseBody>
}