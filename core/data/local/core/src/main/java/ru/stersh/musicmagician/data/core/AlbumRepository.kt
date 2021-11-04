package ru.stersh.musicmagician.data.core

import kotlinx.coroutines.flow.Flow
import ru.stersh.musicmagician.data.core.entity.Album

interface AlbumRepository {
    fun getAllAlbums(): Flow<List<Album>>
    suspend fun getAlbum(id: Int): Album?
    suspend fun updateAlbum(album: Album)
}