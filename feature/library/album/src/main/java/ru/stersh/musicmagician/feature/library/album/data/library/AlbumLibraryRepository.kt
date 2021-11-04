package ru.stersh.musicmagician.feature.library.album.data.library

import kotlinx.coroutines.flow.Flow

interface AlbumLibraryRepository {
    fun getAlbums(): Flow<List<LibraryAlbum>>
}