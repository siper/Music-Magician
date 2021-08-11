package ru.stersh.musicmagician.utils.storio

import android.database.Cursor
import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
import com.pushtorefresh.storio3.contentresolver.operations.get.DefaultGetResolver
import ru.stersh.musicmagician.entity.mediastore.Albumart

class AlbumartStorIOContentResolverGetResolver : DefaultGetResolver<Albumart>() {

    override fun mapFromCursor(storIOContentResolver: StorIOContentResolver,
                               cursor: Cursor): Albumart {
        return Albumart(
                albumId = cursor.getLong(cursor.getColumnIndex("album_id")),
                path = cursor.getString(cursor.getColumnIndex("_data")).orEmpty()
        )
    }
}