package ru.stersh.musicmagician.utils.storio

import android.database.Cursor
import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
import com.pushtorefresh.storio3.contentresolver.operations.get.DefaultGetResolver
import ru.stersh.musicmagician.data.core.entity.Album

class AlbumStorIOContentResolverGetResolver : DefaultGetResolver<ru.stersh.musicmagician.data.core.entity.Album>() {

    override fun mapFromCursor(storIOContentResolver: StorIOContentResolver,
                               cursor: Cursor): ru.stersh.musicmagician.data.core.entity.Album {
        return ru.stersh.musicmagician.data.core.entity.Album(
            id = cursor.getLong(cursor.getColumnIndex("_id")),
            title = cursor.getString(cursor.getColumnIndex("album")).orEmpty(),
            artist = cursor.getString(cursor.getColumnIndex("artist")).orEmpty(),
            albumart = cursor.getString(cursor.getColumnIndex("album_art")).orEmpty(),
            year = cursor.getString(cursor.getColumnIndex("maxyear")).orEmpty()
        )
    }
}