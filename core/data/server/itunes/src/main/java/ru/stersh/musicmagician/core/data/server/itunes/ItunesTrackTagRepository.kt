package ru.stersh.musicmagician.core.data.server.itunes

import ru.stersh.musicmagician.core.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.core.data.server.core.entity.TrackTag
import ru.stersh.musicmagician.core.data.server.itunes.entity.track.ItunesTrackResult

class ItunesTrackTagRepository(private val itunesApi: ItunesApi) : TrackTagRepository {
    override suspend fun getTags(query: String): List<TrackTag> {
        return itunesApi
            .searchTrackTags(query)
            .toTrackTag()
    }

    private fun ItunesTrackResult.toTrackTag(): List<TrackTag> {
        if (resultCount <= 0) {
            return emptyList()
        }
        return results
            .filter { it.kind == "song" }
            .mapNotNull {
                TrackTag(
                    title = it.trackName ?: return@mapNotNull null,
                    artist = it.artistName ?: return@mapNotNull null,
                    album = it.collectionName ?: return@mapNotNull null,
                    albumArtUrl = it.artworkUrl100?.replace("100x100bb", "500x500-100") ?: return@mapNotNull null,
                    genre = it.primaryGenreName,
                    year = it.releaseDate?.substring(0, 4),
                    number = it.trackNumber?.toString()
                )
            }
    }
}