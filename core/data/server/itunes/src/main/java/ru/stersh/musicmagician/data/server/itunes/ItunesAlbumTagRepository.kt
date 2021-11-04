package ru.stersh.musicmagician.data.server.itunes

import ru.stersh.musicmagician.data.server.core.AlbumTagRepository
import ru.stersh.musicmagician.data.server.core.entity.AlbumTag
import ru.stersh.musicmagician.data.server.entity.itunes.album.ItunesAlbumResult

class ItunesAlbumTagRepository(private val itunesApi: ItunesApi) : AlbumTagRepository {
    override suspend fun getTags(query: String): List<AlbumTag> {
//        val query = if (artist.isEmpty()) {
//            album
//        } else {
//            "$artist $album"
//        }
        return itunesApi
            .searchAlbumTags(query)
            .toAlbumTag()
    }

    private fun ItunesAlbumResult.toAlbumTag(): List<AlbumTag> {
        if (this.resultCount <= 0) {
            return emptyList()
        }
        return this
            .results
            .map {
                AlbumTag(
                    album = it.collectionName,
                    artist = it.artistName,
                    albumArtUrl = it.artworkUrl100.replace("100x100bb", "500x500-100"),
                    year = it.releaseDate.substring(0, 4).toInt()
                )
            }
    }
}