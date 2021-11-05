package ru.stersh.musicmagician.feature.editor.track.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onCompletion

class TrackEditorRepositoryImpl : TrackEditorRepository {
    private val track = MutableStateFlow<EditableTrack?>(null)

    override fun getTrack(): Flow<EditableTrack> = track
        .filterNotNull()
        .onCompletion { track.value = null }

    override fun setTrack(track: EditableTrack) {
        this.track.value = track
    }
}