package ru.stersh.musicmagician.core.data.server.deezer

import ru.stersh.musicmagician.core.data.server.core.AlbumTagRepository
import ru.stersh.musicmagician.core.data.server.core.entity.AlbumTag
import ru.stersh.musicmagician.core.data.server.deezer.entity.DeezerTrackResult


class DeezerAlbumTagRepository(private val deezerApi: DeezerApi) : AlbumTagRepository {
    override suspend fun getTags(query: String): List<AlbumTag> {
//        val query = if (artist.isEmpty()) {
//            "album:\"$album\""
//        } else {
//            "album:\"$album\" artist:\"$artist\""
//        }
        return deezerApi.searchAlbumTags(query).toAlbumTag()
    }

    private fun DeezerTrackResult.toAlbumTag(): List<AlbumTag> {
        return this
            .data
            .filter { it.type == "track" }
            .map {
                AlbumTag(
                    album = it.album.title.trim(),
                    albumArtUrl = it.album.coverBig.trim(),
                    artist = it.artist.name.trim(),
                    year = 0
                )
            }
            .distinct()
    }
}