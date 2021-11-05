package ru.stersh.musicmagician.feature.editor.track.domain

import kotlinx.coroutines.flow.Flow
import ru.stersh.musicmagician.data.core.external.ExternalTrackRepository
import ru.stersh.musicmagician.data.core.internal.TrackRepository
import ru.stersh.musicmagician.feature.editor.track.data.EditableTrack
import ru.stersh.musicmagician.feature.editor.track.data.TrackEditorRepository

class TrackEditorInteractor(
    private val trackRepository: TrackRepository,
    private val externalTrackRepository: ExternalTrackRepository,
    private val trackEditorRepository: TrackEditorRepository
) {
    val track: Flow<EditableTrack> = trackEditorRepository.getTrack()

    suspend fun editTrack(id: Long) {
        val localTrack = trackRepository.getTrack(id) ?: throw TrackNotExistsException()
        val externalTrack = externalTrackRepository.getExternalTrack(localTrack.uri) ?: throw TrackNotExistsException()

        val editableTrack = EditableTrack(
            id = localTrack.id,
            uri = localTrack.uri,
            albumId = localTrack.albumId,
            title = localTrack.title,
            artist = localTrack.artist,
            album = localTrack.album,
            albumArt = externalTrack.albumArt,
            trackNumber = externalTrack.trackNumber ?: "",
            comment = externalTrack.comment ?: "",
            year = externalTrack.year ?: "",
            genre = externalTrack.genre ?: "",
            lyrics = externalTrack.lyrics ?: ""
        )
        trackEditorRepository.setTrack(editableTrack)
    }

    suspend fun save() {

    }

    class TrackNotExistsException : Exception()
}