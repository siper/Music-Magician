package ru.stersh.musicmagician.feature.library.album.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.stersh.musicmagician.data.core.internal.AlbumRepository
import ru.stersh.musicmagician.feature.library.album.data.library.AlbumLibraryRepository
import ru.stersh.musicmagician.feature.library.album.data.library.LibraryAlbum

class AlbumLibraryRepositoryImpl(private val albumRepository: AlbumRepository) : AlbumLibraryRepository {
    override fun getAlbums(): Flow<List<LibraryAlbum>> {
        return albumRepository.getAllAlbums().map { albums ->
            albums.map {
                LibraryAlbum(
                    id = it.id,
                    title = it.title,
                    artist = it.artist,
                    albumArtUri = it.albumArtUri,
                    year = it.year
                )
            }
        }
    }
}