package ru.stersh.musicmagician.core.data.server.core

import ru.stersh.musicmagician.core.data.server.core.entity.TrackTag

interface TrackTagRepository {
    suspend fun getTags(query: String): List<TrackTag>
}