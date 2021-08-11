package ru.stersh.musicmagician.model.data.api.itunes

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.stersh.musicmagician.entity.api.itunes.album.ItunesAlbumResult
import ru.stersh.musicmagician.entity.api.itunes.track.ItunesTrackResult

interface ItunesApi {
    @GET("search")
    fun searchTrackTags(
            @Query("term") query: String,
            @Query("limit") limit: Int = 10,
            @Query("entity") entity: String = "musicTrack"
    ): Single<ItunesTrackResult>

    @GET("search")
    fun searchAlbumTags(
            @Query("term") query: String,
            @Query("limit") limit: Int = 10,
            @Query("entity") entity: String = "album"
    ): Single<ItunesAlbumResult>
}