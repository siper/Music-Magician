package ru.stersh.musicmagician.data.mediastore

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stersh.musicmagician.data.core.TrackRepository
import ru.stersh.musicmagician.data.core.entity.Track
import ru.stersh.musicmagician.data.mediastore.extension.getLongOrThrow
import ru.stersh.musicmagician.data.mediastore.extension.getStringOrThrow

class MediastoreTrackRepository(private val contentResolver: ContentResolver) : TrackRepository {
    private val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    private val projection = arrayOf(
        MediaStore.Audio.Media._ID,
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.DATE_ADDED,
        MediaStore.Audio.Media.DATE_MODIFIED,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.TRACK,
        MediaStore.Audio.Media.YEAR,
        MediaStore.Audio.Media.ALBUM_ID,
        MediaStore.Audio.Media.DURATION
    )

    override fun getAllTracks(): Flow<List<Track>> = callbackFlow {
        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                launch {
                    getTracks().run {
                        offer(this)
                    }
                }
            }
        }
        contentResolver.registerContentObserver(uri, true, observer)

        awaitClose {
            contentResolver.unregisterContentObserver(observer)
        }
    }

    override suspend fun getTrack(id: Int): Track? {
        return contentResolver.query(
            uri,
            projection,
            "${MediaStore.Audio.Media._ID} = $id",
            null,
            null
        ).run {
            this?.toTrack().also {
                this?.close()
            }
        }
    }

    override suspend fun updateTrack(track: Track) {
        withContext(Dispatchers.IO) {
            contentResolver.update(
                track.uri,
                track.toContentValues(),
                null,
                null
            )
        }
    }

    private suspend fun getTracks(): List<Track> = withContext(Dispatchers.IO) {
        contentResolver.query(uri, projection, null, null, null).run {
            generateSequence {
                if (this?.moveToNext() == true) {
                    this
                } else {
                    null
                }
            }
                .map { it.toTrack() }
                .toList()
                .also { this?.close() }
        }
    }

    private fun Track.toContentValues(): ContentValues {
        return ContentValues().apply {
            put(MediaStore.Audio.Media._ID, id)
            put(MediaStore.Audio.Media.TITLE, title)
            put(MediaStore.Audio.Media.DATE_ADDED, dateAdded)
            put(MediaStore.Audio.Media.DATE_MODIFIED, dateModified)
            put(MediaStore.Audio.Media.ARTIST, artist)
            put(MediaStore.Audio.Media.ALBUM, album)
            put(MediaStore.Audio.Media.TRACK, trackNumber)
            put(MediaStore.Audio.Media.YEAR, year)
            put(MediaStore.Audio.Media.ALBUM_ID, albumId)
            put(MediaStore.Audio.Media.DURATION, duration)
        }
    }

    private fun Cursor.toTrack(): Track {
        val id = getLongOrThrow(MediaStore.Audio.Media._ID)
        return Track(
            id = id,
            title = getStringOrThrow(MediaStore.Audio.Media.TITLE),
            dateAdded = getLongOrThrow(MediaStore.Audio.Media.DATE_ADDED),
            dateModified = getLongOrThrow(MediaStore.Audio.Media.DATE_MODIFIED),
            artist = getStringOrThrow(MediaStore.Audio.Media.ARTIST),
            album = getStringOrThrow(MediaStore.Audio.Media.ALBUM),
            trackNumber = getStringOrThrow(MediaStore.Audio.Media.TRACK),
            year = getStringOrThrow(MediaStore.Audio.Media.YEAR),
            uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id),
            albumId = getLongOrThrow(MediaStore.Audio.Media.ALBUM_ID),
            duration = getLongOrThrow(MediaStore.Audio.Media.DURATION)
        )
    }
}