package ru.stersh.musicmagician.feature.editor.track.data

import kotlinx.coroutines.flow.Flow

interface TrackEditorRepository {
    fun getTrack(): Flow<EditableTrack>
    fun setTrack(track: EditableTrack)
}