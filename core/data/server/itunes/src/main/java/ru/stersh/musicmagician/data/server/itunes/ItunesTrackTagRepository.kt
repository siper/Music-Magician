package ru.stersh.musicmagician.data.server.itunes

import ru.stersh.musicmagician.data.server.core.TrackTagRepository
import ru.stersh.musicmagician.data.server.core.entity.TrackTag
import ru.stersh.musicmagician.data.server.entity.itunes.track.ItunesTrackResult

class ItunesTrackTagRepository(private val itunesApi: ItunesApi) : TrackTagRepository {
    override suspend fun getTags(query: String): List<TrackTag> {
        return itunesApi
            .searchTrackTags(query)
            .toTrackTag()
    }

    private fun ItunesTrackResult.toTrackTag(): List<TrackTag> {
        if (this.resultCount <= 0) {
            return emptyList()
        }
        return this
            .results
            .filter { it.kind == "song" }
            .map {
                TrackTag(
                    title = it.trackName,
                    artist = it.artistName,
                    album = it.collectionName,
                    albumArtUrl = it.artworkUrl100.replace("100x100bb", "500x500-100"),
                    genre = it.primaryGenreName,
                    year = it.releaseDate.substring(0, 4).toInt(),
                    number = it.trackNumber
                )
            }
    }
}