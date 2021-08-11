package ru.stersh.musicmagician.model.data.api.deezer


import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.stersh.musicmagician.entity.api.deezer.DeezerTrackResult


interface DeezerApi {
    @GET("search")
    fun searchTrackTags(@Query("q") query: String): Single<DeezerTrackResult>

    @GET("search")
    fun searchAlbumTags(@Query("q") query: String): Single<DeezerTrackResult>
}