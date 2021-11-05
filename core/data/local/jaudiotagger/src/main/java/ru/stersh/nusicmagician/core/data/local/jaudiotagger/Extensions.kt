package ru.stersh.nusicmagician.core.data.local.jaudiotagger

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag

fun Tag.getFieldOrNull(key: FieldKey): String? {
    return if (hasField(key)) {
        getFirst(key)
    } else {
        null
    }
}

fun Tag.getAlbumArtOrNull(): Bitmap? {
    if (!hasField(FieldKey.COVER_ART)) {
        return null
    }
    val albumArt = firstArtwork.binaryData
    return BitmapFactory.decodeByteArray(albumArt, 0, albumArt.size)
}