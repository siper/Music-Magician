package ru.stersh.musicmagician.model.data.repository.api.itunes

import io.reactivex.Single
import ru.stersh.musicmagician.entity.api.itunes.track.ItunesTrackResult
import ru.stersh.musicmagician.entity.tag.TrackTag
import ru.stersh.musicmagician.model.data.api.itunes.ItunesApi
import ru.stersh.musicmagician.model.data.repository.api.TrackTagRepository

class ItunesTrackTagRepository(private val itunesApi: ItunesApi) : TrackTagRepository() {
    override fun getTags(title: String, artist: String): Single<List<TrackTag>> {
        val query = if (artist.isEmpty()) {
            title
        } else {
            "$artist $title"
        }
        return itunesApi
                .searchTrackTags(query)
                .map { toTrackTag(it, query) }
    }

    private fun toTrackTag(itunesTrackResult: ItunesTrackResult, query: String): List<TrackTag> {
        if (itunesTrackResult.resultCount <= 0) return emptyList()
        return itunesTrackResult
                .results
                .filter { it.kind == "song" }
                .map {
                    TrackTag(
                            title = it.trackName,
                            artist = it.artistName,
                            album = it.collectionName,
                            albumart = it.artworkUrl100.replace("100x100bb", "500x500-100"),
                            genre = it.primaryGenreName,
                            year = it.releaseDate.substring(0, 4).toInt(),
                            number = it.trackNumber,
                            provider = providerName
                    ).calculatePriority(query)
                }
    }
}