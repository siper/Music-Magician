package ru.stersh.musicmagician.core.data.server.mediator

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.stersh.musicmagician.core.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.core.data.server.core.entity.TrackTag

class MediatorTrackTagRepository(
    private vararg val trackTagRepository: TrackTagRepository
) : TrackTagRepository {
    override suspend fun getTags(query: String): List<TrackTag> = coroutineScope {
        return@coroutineScope trackTagRepository
            .map {
                async { it.getTags(query) }
            }
            .awaitAll()
            .flatten()
    }
}