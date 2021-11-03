package ru.stersh.musicmagician.data.server.itunes

import retrofit2.http.GET
import retrofit2.http.Query
import ru.stersh.musicmagician.data.server.entity.itunes.album.ItunesAlbumResult
import ru.stersh.musicmagician.data.server.entity.itunes.track.ItunesTrackResult

interface ItunesApi {
    @GET("search")
    suspend fun searchTrackTags(
        @Query("term") query: String,
        @Query("limit") limit: Int = 10,
        @Query("entity") entity: String = "musicTrack"
    ): ItunesTrackResult

    @GET("search")
    suspend fun searchAlbumTags(
        @Query("term") query: String,
        @Query("limit") limit: Int = 10,
        @Query("entity") entity: String = "album"
    ): ItunesAlbumResult
}