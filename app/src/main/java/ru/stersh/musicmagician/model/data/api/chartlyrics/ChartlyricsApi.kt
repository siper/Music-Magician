package ru.stersh.musicmagician.model.data.api.chartlyrics

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ChartlyricsApi {
    @GET("apiv1.asmx/SearchLyricDirect")
    fun searchLyrics(@Query("song") title: String, @Query("artist") artist: String): Single<ResponseBody>
}