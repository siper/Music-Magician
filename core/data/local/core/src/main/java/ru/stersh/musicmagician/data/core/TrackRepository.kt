package ru.stersh.musicmagician.data.core

import kotlinx.coroutines.flow.Flow
import ru.stersh.musicmagician.data.core.entity.Track

interface TrackRepository {
    fun getAllTracks(): Flow<List<Track>>
    suspend fun getTrack(id: Int): Track?
    suspend fun updateTrack(track: Track)
}