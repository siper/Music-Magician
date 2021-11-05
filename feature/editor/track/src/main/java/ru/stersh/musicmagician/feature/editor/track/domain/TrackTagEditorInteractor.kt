package ru.stersh.musicmagician.feature.editor.track.domain

import kotlinx.coroutines.flow.first
import ru.stersh.musicmagician.feature.editor.track.data.TrackEditorRepository

class TrackTagEditorInteractor(private val trackEditorRepository: TrackEditorRepository) {
    fun getTrack() = trackEditorRepository.getTrack()

    suspend fun updateTitle(title: String) {
        val track = getTrack().first()
        if (track.title == title) {
            return
        }
        trackEditorRepository.setTrack(track.copy(title = title))
    }

    suspend fun updateAlbum(album: String) {
        val track = getTrack().first()
        if (track.album == album) {
            return
        }
        trackEditorRepository.setTrack(track.copy(album = album))
    }

    suspend fun updateArtist(artist: String) {
        val track = getTrack().first()
        if (track.artist == artist) {
            return
        }
        trackEditorRepository.setTrack(track.copy(artist = artist))
    }

    suspend fun updateComment(comment: String) {
        val track = getTrack().first()
        if (track.comment == comment) {
            return
        }
        trackEditorRepository.setTrack(track.copy(comment = comment))
    }

    suspend fun updateYear(year: String) {
        val track = getTrack().first()
        if (track.year == year) {
            return
        }
        trackEditorRepository.setTrack(track.copy(comment = year))
    }

    suspend fun updateTrackNumber(trackNumber: String) {
        val track = getTrack().first()
        if (track.trackNumber == trackNumber) {
            return
        }
        trackEditorRepository.setTrack(track.copy(trackNumber = trackNumber))
    }

    suspend fun updateGenre(genre: String) {
        val track = getTrack().first()
        if (track.genre == genre) {
            return
        }
        trackEditorRepository.setTrack(track.copy(genre = genre))
    }

    suspend fun updateLyrics(lyrics: String) {
        val track = getTrack().first()
        if (track.lyrics == lyrics) {
            return
        }
        trackEditorRepository.setTrack(track.copy(lyrics = lyrics))
    }
}