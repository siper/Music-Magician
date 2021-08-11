package ru.stersh.musicmagician.model.data.repository.api.deezer

import io.reactivex.Single
import ru.stersh.musicmagician.entity.api.deezer.DeezerTrackResult
import ru.stersh.musicmagician.entity.tag.AlbumTag
import ru.stersh.musicmagician.model.data.api.deezer.DeezerApi
import ru.stersh.musicmagician.model.data.repository.api.AlbumTagRepository


class DeezerAlbumTagRepository(private val deezerApi: DeezerApi) : AlbumTagRepository() {
    override fun getTags(album: String, artist: String): Single<List<AlbumTag>> {
        val query = if (artist.isEmpty()) {
            "album:\"$album\""
        } else {
            "album:\"$album\" artist:\"$artist\""
        }
        return deezerApi
                .searchAlbumTags(query)
                .map { toAlbumTag(it, query) }
    }

    private fun toAlbumTag(resultDeezer: DeezerTrackResult, query: String): List<AlbumTag> {
        return resultDeezer
                .data
                .filter { it.type == "track" }
                .map {
                    AlbumTag(
                            album = it.album.title.trim(),
                            albumart = it.album.coverBig.trim(),
                            artist = it.artist.name.trim(),
                            provider = providerName
                    ).calculatePriority(query)
                }
                .distinct()
    }
}