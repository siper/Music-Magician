package ru.stersh.musicmagician.data.mediastore

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stersh.musicmagician.data.core.AlbumRepository
import ru.stersh.musicmagician.data.core.entity.Album
import ru.stersh.musicmagician.data.mediastore.extension.getLongOrThrow
import ru.stersh.musicmagician.data.mediastore.extension.getStringOrThrow

class MediastoreAlbumRepository(private val contentResolver: ContentResolver) : AlbumRepository {
    private val albumArtUri = Uri.parse("content://media/external/audio/albumart")
    private val uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
    private val projection = arrayOf(
        MediaStore.Audio.Albums._ID,
        MediaStore.Audio.Albums.ARTIST,
        MediaStore.Audio.Albums.ALBUM,
        MediaStore.Audio.Albums.ALBUM_ID
    )

    override fun getAllAlbums(): Flow<List<Album>> = callbackFlow {
        val observer = object : ContentObserver(null) {
            override fun onChange(selfChange: Boolean) {
                launch {
                    getAlbums().run {
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

    override suspend fun getAlbum(id: Int): Album? {
        return contentResolver.query(
            uri,
            projection,
            "${MediaStore.Audio.Albums.ALBUM_ID} = $id",
            null,
            null
        ).run {
            this?.toAlbum().also {
                this?.close()
            }
        }
    }

    override suspend fun updateAlbum(album: Album) {
        withContext(Dispatchers.IO) {
            contentResolver.update(
                ContentUris.withAppendedId(uri, album.id),
                album.toContentValues(),
                null,
                null
            )
        }
    }

    private fun Album.toContentValues(): ContentValues {
        return ContentValues().apply {
            put(MediaStore.Audio.Albums._ID, id)
            put(MediaStore.Audio.Albums.ARTIST, artist)
            put(MediaStore.Audio.Albums.ALBUM, title)
            put(MediaStore.Audio.Albums.ALBUM_ID, id)
        }
    }

    private suspend fun getAlbums(): List<Album> = withContext(Dispatchers.IO) {
        contentResolver.query(uri, projection, null, null, null).run {
            generateSequence {
                if (this?.moveToNext() == true) {
                    this
                } else {
                    null
                }
            }
                .map { it.toAlbum() }
                .toList()
                .also { this?.close() }
        }
    }

    private fun Cursor.toAlbum(): Album {
        val id = getLongOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
        return Album(
            id = id,
            title = getStringOrThrow(MediaStore.Audio.Media.TITLE),
            artist = getStringOrThrow(MediaStore.Audio.Media.ARTIST),
            year = getStringOrThrow(MediaStore.Audio.Media.YEAR),
            albumArtUri = ContentUris.withAppendedId(albumArtUri, id),
        )
    }
}