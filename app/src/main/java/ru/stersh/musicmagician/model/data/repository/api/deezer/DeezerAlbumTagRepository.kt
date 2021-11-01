package ru.stersh.musicmagician.model.data.repository.api.deezer

import io.reactivex.Single
import ru.stersh.musicmagician.entity.api.deezer.DeezerTrackResult
import ru.stersh.musicmagician.data.server.core.entity.AlbumTag
import ru.stersh.musicmagician.model.data.api.deezer.DeezerApi
import ru.stersh.musicmagician.model.data.repository.api.AlbumTagRepository


class DeezerAlbumTagRepository(private val deezerApi: DeezerApi) : AlbumTagRepository() {
    override fun getTags(album: String, artist: String): Single<List<ru.stersh.musicmagician.data.server.core.entity.AlbumTag>> {
        val query = if (artist.isEmpty()) {
            "album:\"$album\""
        } else {
            "album:\"$album\" artist:\"$artist\""
        }
        return deezerApi
                .searchAlbumTags(query)
                .map { toAlbumTag(it, query) }
    }

    private fun toAlbumTag(resultDeezer: DeezerTrackResult, query: String): List<ru.stersh.musicmagician.data.server.core.entity.AlbumTag> {
        return resultDeezer
                .data
                .filter { it.type == "track" }
                .map {
                    ru.stersh.musicmagician.data.server.core.entity.AlbumTag(
                        album = it.album.title.trim(),
                        albumart = it.album.coverBig.trim(),
                        artist = it.artist.name.trim(),
                        provider = providerName
                    ).calculatePriority(query)
                }
                .distinct()
    }
}