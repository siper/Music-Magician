package ru.stersh.nusicmagician.core.data.local.jaudiotagger

import android.content.ContentResolver
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jaudiotagger.tag.flac.FlacTag
import org.jaudiotagger.tag.id3.ID3v1Tag
import org.jaudiotagger.tag.id3.ID3v24Tag
import org.jaudiotagger.tag.mp4.Mp4Tag
import ru.stersh.musicmagician.data.core.external.ExternalTrackRepository
import ru.stersh.musicmagician.data.core.external.entity.ExternalTrack
import java.io.File
import java.io.FileOutputStream

class JAudioTaggerRepository(private val contentResolver: ContentResolver) : ExternalTrackRepository {
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getExternalTrack(uri: Uri): ExternalTrack = withContext(Dispatchers.IO) {
        val stream = contentResolver.openInputStream(uri) ?: error("File not found: $uri")
        val extension = contentResolver.getFileExtension(uri) ?: error("Unsupported file extension")

        val file = File.createTempFile("audio", ".$extension")
        val outputStream = FileOutputStream(file)
        stream.use {
            stream.copyTo(outputStream)
        }
        val audioFile = AudioFileIO.read(file)
        val tag = audioFile.tag

        return@withContext ExternalTrack(
            uri = uri,
            file = file,
            extension = extension,
            title = tag.getFieldOrNull(FieldKey.TITLE),
            artist = tag.getFieldOrNull(FieldKey.ARTIST),
            album = tag.getFieldOrNull(FieldKey.ALBUM),
            year = tag.getFieldOrNull(FieldKey.YEAR),
            genre = tag.getFieldOrNull(FieldKey.GENRE),
            comment = tag.getFieldOrNull(FieldKey.COMMENT),
            lyrics = tag.getFieldOrNull(FieldKey.LYRICS),
            albumArt = tag.getAlbumArtOrNull(),
            trackNumber = tag.getFieldOrNull(FieldKey.TRACK)
        )
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun saveExternalTrack(track: ExternalTrack) {
        val tag = getTag(track).apply {
            trySetField(FieldKey.TITLE, track.title)
            trySetField(FieldKey.ALBUM, track.album)
            trySetField(FieldKey.ARTIST, track.artist)
            trySetField(FieldKey.TRACK, track.trackNumber)
            trySetField(FieldKey.YEAR, track.year)
            trySetField(FieldKey.GENRE, track.genre)
            trySetField(FieldKey.COMMENT, track.comment)
            trySetField(FieldKey.LYRICS, track.lyrics)
            trySetArtwork(track.albumArt)
        }
        val audioFile = AudioFileIO.read(track.file)
        audioFile.tag = tag
        audioFile.commit()
    }

    private fun getTag(externalTrack: ExternalTrack): Tag {
        return when (externalTrack.extension) {
            "wav" -> ID3v1Tag()
            "flac" -> FlacTag()
            "mp3" -> ID3v24Tag()
            "mp4" -> Mp4Tag()
            else -> error("Unsupported file format: ${externalTrack.extension}")
        }
    }
}