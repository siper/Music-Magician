package ru.stersh.musicmagician.data.server.core

import ru.stersh.musicmagician.data.server.core.entity.TrackTag

interface TrackTagRepository {
    suspend fun getTags(query: String): List<TrackTag>
}