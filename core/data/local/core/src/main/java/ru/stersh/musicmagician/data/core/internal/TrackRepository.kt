package ru.stersh.musicmagician.data.core.internal

import kotlinx.coroutines.flow.Flow
import ru.stersh.musicmagician.data.core.internal.entity.Track

interface TrackRepository {
    fun getAllTracks(): Flow<List<Track>>
    suspend fun getTrack(id: Long): Track?
    suspend fun updateTrack(track: Track)
}