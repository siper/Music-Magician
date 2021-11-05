package ru.stersh.musicmagician.core.data.server.core

import ru.stersh.musicmagician.core.data.server.core.entity.AlbumTag

interface AlbumTagRepository {
    suspend fun getTags(query: String): List<AlbumTag>
}