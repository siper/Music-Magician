package ru.stersh.musicmagician.data.core.external

import android.net.Uri
import ru.stersh.musicmagician.data.core.external.entity.ExternalTrack

interface ExternalTrackRepository {
    suspend fun getExternalTrack(uri: Uri): ExternalTrack?
    suspend fun saveExternalTrack(track: ExternalTrack)
}