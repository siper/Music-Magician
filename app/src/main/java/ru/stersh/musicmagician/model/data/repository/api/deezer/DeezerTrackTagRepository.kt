package ru.stersh.musicmagician.model.data.repository.api.deezer

import io.reactivex.Single
import ru.stersh.musicmagician.entity.api.deezer.DeezerTrackResult
import ru.stersh.musicmagician.data.server.core.entity.TrackTag
import ru.stersh.musicmagician.model.data.api.deezer.DeezerApi
import ru.stersh.musicmagician.model.data.repository.api.TrackTagRepository


class DeezerTrackTagRepository(private val deezerApi: DeezerApi) : TrackTagRepository() {
    override fun getTags(title: String, artist: String): Single<List<ru.stersh.musicmagician.data.server.core.entity.TrackTag>> {
        val query = if (artist.isEmpty()) {
            "track:\"$title\""
        } else {
            "track:\"$title\" artist:\"$artist\""
        }
        return deezerApi
                .searchTrackTags(query)
                .map { toTrackTag(it, query) }
    }

    private fun toTrackTag(deezerTrackResult: DeezerTrackResult, query: String): List<ru.stersh.musicmagician.data.server.core.entity.TrackTag> {
        return deezerTrackResult
                .data
                .filter { it.type == "track" }
                .map {
                    ru.stersh.musicmagician.data.server.core.entity.TrackTag(
                        title = it.title.trim(),
                        albumart = it.album.coverBig,
                        artist = it.artist.name.trim(),
                        album = it.album.title.trim(),
                        provider = providerName
                    ).calculatePriority(query)
                }
    }
}