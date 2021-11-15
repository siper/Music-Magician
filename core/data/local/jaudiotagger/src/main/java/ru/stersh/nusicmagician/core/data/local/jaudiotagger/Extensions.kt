package ru.stersh.nusicmagician.core.data.local.jaudiotagger

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.webkit.MimeTypeMap
import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture
import org.jaudiotagger.tag.FieldDataInvalidException
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jaudiotagger.tag.flac.FlacTag
import org.jaudiotagger.tag.id3.ID3v24Tag
import org.jaudiotagger.tag.id3.valuepair.ImageFormats
import org.jaudiotagger.tag.images.ArtworkFactory
import org.jaudiotagger.tag.mp4.Mp4Tag
import org.jaudiotagger.tag.reference.PictureTypes
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.nio.ByteBuffer


fun Tag.getFieldOrNull(key: FieldKey): String? {
    return if (hasField(key)) {
        getFirst(key)
    } else {
        null
    }
}

fun Tag.trySetField(fieldKey: FieldKey, data: String?) {
    if (data.isNullOrEmpty()) {
        return
    }
    try {
        setField(fieldKey, data)
    } catch (e: FieldDataInvalidException) {
        Timber.d("Error setting $data into field: ${fieldKey.name}")
    }
}

fun Tag.trySetArtwork(bitmap: Bitmap?) {
    if (bitmap == null) {
        return
    }

    if (this !is ID3v24Tag || this !is FlacTag || this !is Mp4Tag) {
        return
    }

    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    val bytes: ByteArray = stream.toByteArray()

    if (this is ID3v24Tag) {
        val art = ArtworkFactory.createArtworkFromMetadataBlockDataPicture(
            MetadataBlockDataPicture(
                ByteBuffer.wrap(bytes)
            )
        )
        setField(art)
        return
    }

    if (this is FlacTag) {
        try {
            setField(
                createArtworkField(
                    bytes,
                    PictureTypes.DEFAULT_ID,
                    ImageFormats.MIME_TYPE_JPG,
                    "coverart", bitmap.width, bitmap.height, 24, 0
                )
            )
        } catch (e: IOException) {
            Timber.d("Error setting albumart")
        } catch (e: FieldDataInvalidException) {
            Timber.d("Error setting albumart")
        }
        return
    }

    if (this is Mp4Tag) {
        try {
            addField(createArtworkField(bytes))
        } catch (e: IOException) {
            Timber.d("Error setting albumart")
        }
    }
}

fun Tag.getAlbumArtOrNull(): Bitmap? {
    if (!hasField(FieldKey.COVER_ART)) {
        return null
    }
    val albumArt = firstArtwork.binaryData
    return BitmapFactory.decodeByteArray(albumArt, 0, albumArt.size)
}

fun ContentResolver.getFileExtension(uri: Uri): String? {
    val mime = MimeTypeMap.getSingleton()
    return mime.getExtensionFromMimeType(getType(uri))
}