package ru.stersh.musicmagician.feature.editor.track.domain

import ru.stersh.musicmagician.data.server.core.LyricTagRepository
import ru.stersh.musicmagician.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.feature.editor.track.data.TrackEditorRepository

class TrackTagSearchInteractor(
    private val trackEditorRepository: TrackEditorRepository,
    private val trackTagRepository: TrackTagRepository,
    private val lyricTagRepository: LyricTagRepository
) {
    fun getTrack() = trackEditorRepository.getTrack()

    suspend fun getTrackTags(query: String) = trackTagRepository.getTags(query)
    suspend fun getLyricTags(title: String, artist: String) = lyricTagRepository.getTags(title, artist)
}