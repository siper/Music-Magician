package ru.stersh.musicmagician.model.data.repository.api.itunes

import io.reactivex.Single
import ru.stersh.musicmagician.entity.api.itunes.album.ItunesAlbumResult
import ru.stersh.musicmagician.entity.tag.AlbumTag
import ru.stersh.musicmagician.model.data.api.itunes.ItunesApi
import ru.stersh.musicmagician.model.data.repository.api.AlbumTagRepository

class ItunesAlbumTagRepository(private val itunesApi: ItunesApi) : AlbumTagRepository() {
    override fun getTags(album: String, artist: String): Single<List<AlbumTag>> {
        val query = if (artist.isEmpty()) {
            album
        } else {
            "$artist $album"
        }
        return itunesApi
                .searchAlbumTags(query)
                .map { toAlbumTag(it, query) }
    }

    private fun toAlbumTag(itunesAlbumResult: ItunesAlbumResult, query: String): List<AlbumTag> {
        if (itunesAlbumResult.resultCount <= 0) return emptyList()
        return itunesAlbumResult
                .results
                .map {
                    AlbumTag(
                            album = it.collectionName,
                            artist = it.artistName,
                            albumart = it.artworkUrl100.replace("100x100bb", "500x500-100"),
                            year = it.releaseDate.substring(0, 4).toInt(),
                            provider = providerName
                    ).calculatePriority(query)
                }
    }
}