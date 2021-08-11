package ru.stersh.musicmagician.utils.storio

import android.database.Cursor
import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
import com.pushtorefresh.storio3.contentresolver.operations.get.DefaultGetResolver
import ru.stersh.musicmagician.entity.mediastore.Album

class AlbumStorIOContentResolverGetResolver : DefaultGetResolver<Album>() {

    override fun mapFromCursor(storIOContentResolver: StorIOContentResolver,
                               cursor: Cursor): Album {
        return Album(
                id = cursor.getLong(cursor.getColumnIndex("_id")),
                title = cursor.getString(cursor.getColumnIndex("album")).orEmpty(),
                artist = cursor.getString(cursor.getColumnIndex("artist")).orEmpty(),
                albumart = cursor.getString(cursor.getColumnIndex("album_art")).orEmpty(),
                year = cursor.getString(cursor.getColumnIndex("maxyear")).orEmpty()
        )
    }
}