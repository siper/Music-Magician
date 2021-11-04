package ru.stersh.musicmagician.utils.tag

import android.text.TextUtils
import org.jaudiotagger.tag.FieldDataInvalidException
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jaudiotagger.tag.flac.FlacTag
import org.jaudiotagger.tag.id3.ID3v1Tag
import org.jaudiotagger.tag.id3.ID3v24Tag
import org.jaudiotagger.tag.mp4.Mp4Tag
import timber.log.Timber
import java.nio.charset.StandardCharsets

object TrackUtils {
    private fun getFlacTag(track: ru.stersh.musicmagician.data.core.entity.Track): FlacTag {
        val tag = FlacTag()
//        if (!TextUtils.isEmpty(track.title)) {
//            try {
//                tag.addField(FieldKey.TITLE, track.title)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting title")
//            }
//        }
//        if (!TextUtils.isEmpty(track.album)) {
//            try {
//                tag.addField(FieldKey.ALBUM, track.album)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting album")
//            }
//        }
//        if (!TextUtils.isEmpty(track.artist)) {
//            try {
//                tag.addField(FieldKey.ARTIST, track.artist)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting artist")
//            }
//        }
//        if (!TextUtils.isEmpty(track.trackNumber)) {
//            try {
//                tag.addField(FieldKey.TRACK, track.trackNumber)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting trackNumber")
//            }
//        }
//        if (!TextUtils.isEmpty(track.year)) {
//            try {
//                tag.addField(FieldKey.YEAR, track.year)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting year")
//            }
//        }
//        if (!TextUtils.isEmpty(track.genre)) {
//            try {
//                tag.addField(FieldKey.GENRE, track.genre)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting genre")
//            }
//        }
//        if (!TextUtils.isEmpty(track.comment)) {
//            try {
//                tag.addField(FieldKey.COMMENT, track.comment)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting comment")
//            }
//        }
//        if (!TextUtils.isEmpty(track.lyrics)) {
//            try {
//                tag.addField(FieldKey.LYRICS, track.lyrics)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting lyrics")
//            }
//        }
//        if (!TextUtils.isEmpty(track.albumart)) {
//            try {
//                val coverFile = File(track.albumart)
//                val imageFile = RandomAccessFile(coverFile, "r")
//                val imagedata = ByteArray(imageFile.length().toInt())
//                imageFile.read(imagedata)
//                tag.setField(
//                    tag.createArtworkField(
//                        imagedata,
//                        PictureTypes.DEFAULT_ID,
//                        ImageFormats.MIME_TYPE_JPG,
//                        "coverart", 400, 400, 24, 0
//                    )
//                )
//            } catch (e: IOException) {
//                Timber.d("Error setting albumart")
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting albumart")
//            }
//        }
        return tag
    }

    private fun getID3v1Tag(track: ru.stersh.musicmagician.data.core.entity.Track): Tag {
        val tag: Tag = ID3v1Tag()
//        if (!TextUtils.isEmpty(track.title)) {
//            try {
//                var text = track.title
//                text = String(text.toByteArray(), StandardCharsets.ISO_8859_1)
//                tag.setField(FieldKey.TITLE, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save title")
//            }
//        }
//        if (!TextUtils.isEmpty(track.album)) {
//            try {
//                var text = track.album
//                text = String(text.toByteArray(), StandardCharsets.ISO_8859_1)
//                tag.setField(FieldKey.ALBUM, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save title")
//            }
//        }
//        if (!TextUtils.isEmpty(track.artist)) {
//            try {
//                var text = track.artist
//                text = String(text.toByteArray(), StandardCharsets.ISO_8859_1)
//                tag.setField(FieldKey.ARTIST, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save artist")
//            }
//        }
//        if (!TextUtils.isEmpty(track.trackNumber)) {
//            try {
//                var text = track.trackNumber
//                text = String(text.toByteArray(), StandardCharsets.ISO_8859_1)
//                tag.setField(FieldKey.TRACK, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save track")
//            }
//        }
//        if (!TextUtils.isEmpty(track.year)) {
//            try {
//                var text = track.year
//                text = String(text.toByteArray(), StandardCharsets.ISO_8859_1)
//                tag.setField(FieldKey.YEAR, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save year")
//            }
//        }
////        if (!TextUtils.isEmpty(track.genre)) {
////            try {
////                var text = track.genre
////                text = String(text.toByteArray(), StandardCharsets.ISO_8859_1)
////                tag.setField(FieldKey.GENRE, text)
////            } catch (e: FieldDataInvalidException) {
////                Timber.d("Filed to save genre")
////            }
////        }
////        if (!TextUtils.isEmpty(track.comment)) {
////            try {
////                var text = track.comment
////                text = String(text.toByteArray(), StandardCharsets.ISO_8859_1)
////                tag.setField(FieldKey.COMMENT, text)
////            } catch (e: FieldDataInvalidException) {
////                Timber.d("Filed to save comment")
////            }
////        }
//        return tag
//    }
//
//    private fun getID3v24Tag(track: ru.stersh.musicmagician.data.core.entity.Track): Tag {
//        val tag: Tag = ID3v24Tag()
//        if (!TextUtils.isEmpty(track.title)) {
//            try {
//                val text = track.title
//                tag.setField(FieldKey.TITLE, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save title")
//            }
//        }
//        if (!TextUtils.isEmpty(track.album)) {
//            try {
//                val text = track.album
//                tag.setField(FieldKey.ALBUM, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save album")
//            }
//        }
//        if (!TextUtils.isEmpty(track.artist)) {
//            try {
//                val text = track.artist
//                tag.setField(FieldKey.ARTIST, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save artist")
//            }
//        }
//        if (!TextUtils.isEmpty(track.trackNumber)) {
//            try {
//                val text = track.trackNumber
//                tag.setField(FieldKey.TRACK, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save tracknumber")
//            }
//        }
//        if (!TextUtils.isEmpty(track.year)) {
//            try {
//                val text = track.year
//                tag.setField(FieldKey.YEAR, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save year")
//            }
//        }
//        if (!TextUtils.isEmpty(track.genre)) {
//            try {
//                val text = track.genre
//                tag.setField(FieldKey.GENRE, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save genre")
//            }
//        }
//        if (!TextUtils.isEmpty(track.comment)) {
//            try {
//                val text = track.comment
//                tag.setField(FieldKey.COMMENT, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save comment")
//            }
//        }
//        if (!TextUtils.isEmpty(track.lyrics)) {
//            try {
//                val text = track.lyrics
//                tag.setField(FieldKey.LYRICS, text)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Filed to save comment")
//            }
//        }
//        try {
//            if (!TextUtils.isEmpty(track.albumart)) {
//                val coverFile = File(track.albumart)
//                val art = ArtworkFactory.createArtworkFromFile(coverFile)
//                tag.addField(art)
//                tag.setField(art)
//            }
//        } catch (e: IOException) {
//            Timber.d("Filed to save albumart")
//        } catch (e: FieldDataInvalidException) {
//            Timber.d("Filed to save albumart")
//        } catch (e: NullPointerException) {
//            Timber.d("Filed to save albumart")
//        }
        return tag
    }

    private fun getMp4Tag(track: ru.stersh.musicmagician.data.core.entity.Track): Mp4Tag {
        val tag = Mp4Tag()
//        if (!TextUtils.isEmpty(track.title)) {
//            try {
//                tag.addField(FieldKey.TITLE, track.title)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting title")
//            }
//        }
//        if (!TextUtils.isEmpty(track.album)) {
//            try {
//                tag.addField(FieldKey.ALBUM, track.album)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting album")
//            }
//        }
//        if (!TextUtils.isEmpty(track.artist)) {
//            try {
//                tag.addField(FieldKey.ARTIST, track.artist)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting artist")
//            }
//        }
//        if (!TextUtils.isEmpty(track.trackNumber)) {
//            try {
//                tag.addField(FieldKey.TRACK, track.trackNumber)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting trackNumber")
//            }
//        }
//        if (!TextUtils.isEmpty(track.year)) {
//            try {
//                tag.addField(FieldKey.YEAR, track.year)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting year")
//            }
//        }
//        if (!TextUtils.isEmpty(track.genre)) {
//            try {
//                tag.addField(FieldKey.GENRE, track.genre)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting genre")
//            }
//        }
//        if (!TextUtils.isEmpty(track.comment)) {
//            try {
//                tag.addField(FieldKey.COMMENT, track.comment)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting comment")
//            }
//        }
//        if (!TextUtils.isEmpty(track.lyrics)) {
//            try {
//                tag.addField(FieldKey.LYRICS, track.lyrics)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting lyrics")
//            }
//        }
//        if (!TextUtils.isEmpty(track.albumart)) {
//            try {
//                val coverFile = File(track.albumart)
//                val imageFile = RandomAccessFile(coverFile, "r")
//                val imagedata = ByteArray(imageFile.length().toInt())
//                imageFile.read(imagedata)
//                tag.addField(tag.createArtworkField(imagedata))
//            } catch (e: IOException) {
//                Timber.d("Error setting albumart")
//            }
//        }
        return tag
    }

    private fun getWavTag(track: ru.stersh.musicmagician.data.core.entity.Track): ID3v1Tag {
        val tag = ID3v1Tag()
//        if (!TextUtils.isEmpty(track.title)) {
//            try {
//                tag.addField(FieldKey.TITLE, track.title)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting title")
//            }
//        }
//        if (!TextUtils.isEmpty(track.album)) {
//            try {
//                tag.addField(FieldKey.ALBUM, track.album)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting album")
//            }
//        }
//        if (!TextUtils.isEmpty(track.artist)) {
//            try {
//                tag.addField(FieldKey.ARTIST, track.artist)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting artist")
//            }
//        }
//        if (!TextUtils.isEmpty(track.trackNumber)) {
//            try {
//                tag.addField(FieldKey.TRACK, track.trackNumber)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting trackNumber")
//            }
//        }
//        if (!TextUtils.isEmpty(track.year)) {
//            try {
//                tag.addField(FieldKey.YEAR, track.year)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting year")
//            }
//        }
//        if (!TextUtils.isEmpty(track.genre)) {
//            try {
//                tag.addField(FieldKey.GENRE, track.genre)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting genre")
//            }
//        }
//        if (!TextUtils.isEmpty(track.comment)) {
//            try {
//                tag.addField(FieldKey.COMMENT, track.comment)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting comment")
//            }
//        }
//        if (!TextUtils.isEmpty(track.lyrics)) {
//            try {
//                tag.addField(FieldKey.LYRICS, track.lyrics)
//            } catch (e: FieldDataInvalidException) {
//                Timber.d("Error setting lyrics")
//            }
//        }
        return tag
    }

    fun save(track: ru.stersh.musicmagician.data.core.entity.Track) {
//        try {
//            val audio = AudioFileIO().readFile(File(track.path))
//            val path = track.path.lowercase(Locale.ROOT)
//            when {
//                path.endsWith(".flac") -> {
//                    audio.tag = getFlacTag(track)
//                }
//                path.endsWith(".mp4") -> {
//                    audio.tag = getMp4Tag(track)
//                }
//                path.endsWith(".wav") -> {
//                    audio.tag = getWavTag(track)
//                }
//                else -> {
//                    audio.tag = getID3v1Tag(track)
//                    audio.tag = getID3v24Tag(track)
//                }
//            }
//            audio.commit()
//        } catch (e: Exception) {
//            Timber.d(e, "Error saving track")
//        }
    }
}