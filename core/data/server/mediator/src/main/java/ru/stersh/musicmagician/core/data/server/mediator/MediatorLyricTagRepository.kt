package ru.stersh.musicmagician.core.data.server.mediator

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.stersh.musicmagician.core.data.server.core.LyricTagRepository
import ru.stersh.musicmagician.core.data.server.core.entity.LyricsTag

class MediatorLyricTagRepository(
    private vararg val lyricTagRepository: LyricTagRepository
) : LyricTagRepository {
    override suspend fun getTags(title: String, artist: String): List<LyricsTag> = coroutineScope {
        return@coroutineScope lyricTagRepository
            .map {
                async { it.getTags(title, artist) }
            }
            .awaitAll()
            .flatten()
    }
}