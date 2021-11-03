package ru.stersh.musicmagician.feature.library.track.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import ru.stersh.musicmagician.data.core.AlbumRepository
import ru.stersh.musicmagician.data.core.TrackRepository
import ru.stersh.musicmagician.feature.library.track.data.library.LibraryTrack
import ru.stersh.musicmagician.feature.library.track.data.library.TrackLibraryRepository

class TrackLibraryRepositoryImpl(
    private val trackRepository: TrackRepository,
    private val albumRepository: AlbumRepository
) : TrackLibraryRepository {
    override fun getTracks(): Flow<List<LibraryTrack>> {
        return combine(trackRepository.getAllTracks(), albumRepository.getAllAlbums()) { tracks, albums ->
            tracks.map { track ->
                val albumArtUri = albums.firstOrNull { it.id == track.albumId }?.albumArtUri
                LibraryTrack(
                    id = track.id,
                    title = track.title,
                    artist = track.artist,
                    dateAdded = track.dateAdded,
                    album = track.album,
                    uri = track.uri,
                    albumArtUri = albumArtUri
                )
            }
        }
    }
}