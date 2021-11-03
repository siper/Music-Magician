package ru.stersh.musicmagician.feature.library.track.data.library

import kotlinx.coroutines.flow.Flow

interface TrackLibraryRepository {
    fun getTracks(): Flow<List<LibraryTrack>>
}