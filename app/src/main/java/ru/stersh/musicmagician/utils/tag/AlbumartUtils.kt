package ru.stersh.musicmagician.utils.tag

import android.content.ContentUris
import android.graphics.Bitmap
import android.provider.MediaStore
import com.pushtorefresh.storio3.contentresolver.StorIOContentResolver
import com.pushtorefresh.storio3.contentresolver.queries.Query
import com.squareup.picasso.Picasso
import org.koin.core.component.get
import ru.stersh.musicmagician.albumartUri
import ru.stersh.musicmagician.Di
import ru.stersh.musicmagician.tempAlbumart
import ru.stersh.musicmagician.thumbnails
import ru.stersh.musicmagician.utils.ImageUtil
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

object AlbumartUtils {
    private val storIOContentResolver = Di.get<StorIOContentResolver>()

    fun delete(id: Long) {
//        val albumart = storIOContentResolver
//            .get()
//            .`object`(ru.stersh.musicmagician.data.core.entity.Albumart::class.java)
//            .withQuery(
//                Query
//                    .builder()
//                    .uri(ContentUris.withAppendedId(albumartUri, id))
//                    .build()
//            )
//            .prepare()
//            .executeAsBlocking()
//        if (albumart != null) {
//            File(albumart.path).apply {
//                if (exists()) delete()
//            }
//
//            storIOContentResolver
//                .delete()
//                .`object`(albumart)
//                .prepare()
//                .executeAsBlocking()
//        }
    }

    fun put(id: Long, newAlbumart: String) {
        var albumart = storIOContentResolver
            .get()
            .`object`(ru.stersh.musicmagician.data.core.entity.Albumart::class.java)
            .withQuery(
                Query
                    .builder()
                    .uri(ContentUris.withAppendedId(albumartUri, id))
                    .build()
            )
            .prepare()
            .executeAsBlocking()
        if (albumart != null) {
            File(albumart.path).apply {
               // if (exists()) delete()
            }
        }
        albumart = ru.stersh.musicmagician.data.core.entity.Albumart(
            albumId = id,
            path = generateThumbnail(newAlbumart)
        )
        storIOContentResolver
            .put()
            .`object`(albumart)
            .prepare()
            .executeAsBlocking()
        storIOContentResolver
            .lowLevel()
            .contentResolver()
            .notifyChange(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null)
        storIOContentResolver
            .lowLevel()
            .contentResolver()
            .notifyChange(albumartUri, null)
        Picasso.get().invalidate(File(albumart.path))
    }

    private fun generateThumbnail(albumartPath: String): String {
        val folder = File(thumbnails).apply {
            if (!exists()) mkdirs()
        }
        val output = folder.absolutePath + "/" + System.currentTimeMillis().toString()
        return ImageUtil.compressImage(
            File(albumartPath),
            300f,
            300f,
            Bitmap.CompressFormat.JPEG,
            75,
            output
        ).absolutePath
    }

    fun saveAlbumartToSd(bmp: Bitmap) {
        try {
            val file = File(tempAlbumart).apply {
                if (exists()) delete()
            }
            val fOut = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 95, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: java.lang.Exception) {
            Timber.e(e)
        }
    }
}