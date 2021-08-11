package ru.stersh.musicmagician.utils.storio

import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import com.pushtorefresh.storio3.contentresolver.operations.put.DefaultPutResolver
import com.pushtorefresh.storio3.contentresolver.queries.InsertQuery
import com.pushtorefresh.storio3.contentresolver.queries.UpdateQuery
import ru.stersh.musicmagician.entity.mediastore.Album

class AlbumStorIOContentResolverPutResolver : DefaultPutResolver<Album>() {
    private val albumsUri = Uri.parse("content://media/external/audio/albums")
    override fun mapToInsertQuery(`object`: Album): InsertQuery {
        return InsertQuery.builder()
                .uri(albumsUri)
                .build()
    }

    override fun mapToUpdateQuery(album: Album): UpdateQuery {
        return UpdateQuery.builder()
                .uri(ContentUris.withAppendedId(albumsUri, album.id))
                .build()
    }

    override fun mapToContentValues(album: Album): ContentValues {
        val values = ContentValues()
        values.put("album_id", album.id)
        values.put("album", album.title)
        return values
    }
}