package ru.stersh.musicmagician.core.data.server.core

import ru.stersh.musicmagician.core.data.server.core.entity.LyricsTag

interface LyricTagRepository {
    suspend fun getTags(title: String, artist: String): List<LyricsTag>
}