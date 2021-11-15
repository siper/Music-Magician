package ru.stersh.musicmagician.feature.editor.track.domain

import kotlinx.coroutines.flow.first
import ru.stersh.musicmagician.core.data.server.core.LyricTagRepository
import ru.stersh.musicmagician.core.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.core.utils.BitmapLoader
import ru.stersh.musicmagician.feature.editor.track.data.TrackEditorRepository

class TrackTagSearchInteractor(
    private val trackEditorRepository: TrackEditorRepository,
    private val trackTagRepository: TrackTagRepository,
    private val lyricTagRepository: LyricTagRepository,
    private val bitmapLoader: BitmapLoader
) {
    fun getTrack() = trackEditorRepository.getTrack()

    suspend fun getTrackTags(query: String) = trackTagRepository.getTags(query)
    suspend fun getLyricTags(title: String, artist: String) = lyricTagRepository.getTags(title, artist)

    suspend fun setLyrics(lyrics: String) {
        val track = getTrack().first()
        trackEditorRepository.setTrack(track.copy(lyrics = lyrics))
    }

    suspend fun setTags(
        title: String,
        artist: String,
        album: String,
        albumArtUrl: String,
        genre: String?,
        year: String?,
        trackNumber: String?
    ) {
        val track = getTrack().first()
        val albumArt = bitmapLoader.load(albumArtUrl)
        val newTrack = track.copy(
            title = title,
            artist = artist,
            album = album,
            genre = genre ?: track.genre,
            year = year ?: track.year,
            trackNumber = trackNumber ?: track.trackNumber,
            albumArt = albumArt
        )
        trackEditorRepository.setTrack(newTrack)
    }
}