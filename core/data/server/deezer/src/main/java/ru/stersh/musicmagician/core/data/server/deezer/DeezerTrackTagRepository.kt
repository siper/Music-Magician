package ru.stersh.musicmagician.core.data.server.deezer

import ru.stersh.musicmagician.core.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.core.data.server.core.entity.TrackTag
import ru.stersh.musicmagician.core.data.server.deezer.entity.DeezerTrackResult


class DeezerTrackTagRepository(private val deezerApi: DeezerApi) : TrackTagRepository {
    override suspend fun getTags(query: String): List<TrackTag> {
//        val query = if (artist.isEmpty()) {
//            "track:\"$title\""
//        } else {
//            "track:\"$title\" artist:\"$artist\""
//        }
        return deezerApi.searchTrackTags(query).toTrackTag()
    }

    private fun DeezerTrackResult.toTrackTag(): List<TrackTag> {
        return this
            .data
            .filter { it.type == "track" }
            .map {
                TrackTag(
                    title = it.title.trim(),
                    albumArtUrl = it.album.coverBig,
                    artist = it.artist.name.trim(),
                    album = it.album.title.trim(),
                    year = null,
                    genre = null,
                    number = null
                )
            }
    }
}