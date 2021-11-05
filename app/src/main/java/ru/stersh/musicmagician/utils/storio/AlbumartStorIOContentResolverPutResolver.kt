package ru.stersh.musicmagician.utils.storio

import android.content.ContentUris
import android.content.ContentValues
import com.pushtorefresh.storio3.contentresolver.operations.put.DefaultPutResolver
import com.pushtorefresh.storio3.contentresolver.queries.InsertQuery
import com.pushtorefresh.storio3.contentresolver.queries.UpdateQuery
import ru.stersh.musicmagician.albumartUri

class AlbumartStorIOContentResolverPutResolver :
    DefaultPutResolver<ru.stersh.musicmagician.data.core.internal.entity.Albumart>() {
    override fun mapToInsertQuery(albumart: ru.stersh.musicmagician.data.core.internal.entity.Albumart): InsertQuery {
        return InsertQuery.builder()
            .uri(albumartUri)
            .build()
    }

    override fun mapToUpdateQuery(albumart: ru.stersh.musicmagician.data.core.internal.entity.Albumart): UpdateQuery {
        return UpdateQuery.builder()
            .uri(ContentUris.withAppendedId(albumartUri, albumart.albumId))
            .build()
    }

    override fun mapToContentValues(albumart: ru.stersh.musicmagician.data.core.internal.entity.Albumart): ContentValues {
        val values = ContentValues()
        values.put("album_id", albumart.albumId)
        values.put("_data", albumart.path)
        return values
    }
}