package ru.stersh.musicmagician.core.data.server.mediator

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.stersh.musicmagician.core.data.server.core.AlbumTagRepository
import ru.stersh.musicmagician.core.data.server.core.entity.AlbumTag

class MediatorAlbumTagRepository(
    private vararg val albumTagRepository: AlbumTagRepository
) : AlbumTagRepository {
    override suspend fun getTags(query: String): List<AlbumTag> = coroutineScope {
        return@coroutineScope albumTagRepository
            .map {
                async { it.getTags(query) }
            }
            .awaitAll()
            .flatten()
    }
}