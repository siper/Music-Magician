package ru.stersh.musicmagician.data.server.core

import ru.stersh.musicmagician.data.server.core.entity.AlbumTag

interface AlbumTagRepository {
    suspend fun getTags(query: String): List<AlbumTag>
}