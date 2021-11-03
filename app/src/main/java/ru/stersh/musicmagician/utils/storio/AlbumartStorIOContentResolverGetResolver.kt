package ru.stersh.musicmagician.utils.storio

import android.database.Cursor
import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
import com.pushtorefresh.storio3.contentresolver.operations.get.DefaultGetResolver

class AlbumartStorIOContentResolverGetResolver :
    DefaultGetResolver<ru.stersh.musicmagician.data.core.entity.Albumart>() {

    override fun mapFromCursor(
        storIOContentResolver: StorIOContentResolver,
        cursor: Cursor
    ): ru.stersh.musicmagician.data.core.entity.Albumart {
        return ru.stersh.musicmagician.data.core.entity.Albumart(
            albumId = cursor.getLong(cursor.getColumnIndex("album_id")),
            path = cursor.getString(cursor.getColumnIndex("_data")).orEmpty()
        )
    }
}