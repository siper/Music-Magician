package ru.stersh.musicmagician.utils.storio

import android.content.ContentUris
import android.database.Cursor
import android.provider.MediaStore
import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
import com.pushtorefresh.storio3.contentresolver.operations.get.DefaultGetResolver

class TrackStorIOContentResolverGetResolver : DefaultGetResolver<ru.stersh.musicmagician.data.core.entity.Track>() {

    override fun mapFromCursor(
        storIOContentResolver: StorIOContentResolver,
        cursor: Cursor
    ): ru.stersh.musicmagician.data.core.entity.Track {
        val trackId = cursor.getLong(cursor.getColumnIndex("_id"))
        return ru.stersh.musicmagician.data.core.entity.Track(
            id = trackId,
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)),
            uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, trackId),
            title = cursor.getString(cursor.getColumnIndex("title")).orEmpty(),
            dateAdded = cursor.getLong(cursor.getColumnIndex("date_added")),
            dateModified = cursor.getLong(cursor.getColumnIndex("date_modified")),
            artist = cursor.getString(cursor.getColumnIndex("artist")).orEmpty(),
            album = cursor.getString(cursor.getColumnIndex("album")).orEmpty(),
            trackNumber = cursor.getString(cursor.getColumnIndex("track")).orEmpty(),
            year = cursor.getString(cursor.getColumnIndex("year")).orEmpty(),
            albumId = cursor.getLong(cursor.getColumnIndex("album_id")),
            duration = cursor.getLong(cursor.getColumnIndex("duration"))
        )
    }
}