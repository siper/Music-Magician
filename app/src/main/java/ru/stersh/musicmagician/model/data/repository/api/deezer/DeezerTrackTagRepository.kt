package ru.stersh.musicmagician.model.data.repository.api.deezer

import io.reactivex.Single
import ru.stersh.musicmagician.entity.api.deezer.DeezerTrackResult
import ru.stersh.musicmagician.entity.tag.TrackTag
import ru.stersh.musicmagician.model.data.api.deezer.DeezerApi
import ru.stersh.musicmagician.model.data.repository.api.TrackTagRepository


class DeezerTrackTagRepository(private val deezerApi: DeezerApi) : TrackTagRepository() {
    override fun getTags(title: String, artist: String): Single<List<TrackTag>> {
        val query = if (artist.isEmpty()) {
            "track:\"$title\""
        } else {
            "track:\"$title\" artist:\"$artist\""
        }
        return deezerApi
                .searchTrackTags(query)
                .map { toTrackTag(it, query) }
    }

    private fun toTrackTag(deezerTrackResult: DeezerTrackResult, query: String): List<TrackTag> {
        return deezerTrackResult
                .data
                .filter { it.type == "track" }
                .map {
                    TrackTag(
                            title = it.title.trim(),
                            albumart = it.album.coverBig,
                            artist = it.artist.name.trim(),
                            album = it.album.title.trim(),
                            provider = providerName
                    ).calculatePriority(query)
                }
    }
}