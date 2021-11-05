package ru.stersh.nusicmagician.core.data.local.jaudiotagger

import android.content.ContentResolver
import android.net.Uri
import com.anggrayudi.storage.file.DocumentFileCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import ru.stersh.musicmagician.data.core.external.ExternalTrackRepository
import ru.stersh.musicmagician.data.core.external.entity.ExternalTrack
import java.io.File
import java.io.FileOutputStream

class JAudioTaggerRepository(private val contentResolver: ContentResolver) : ExternalTrackRepository {
    override suspend fun getExternalTrack(uri: Uri): ExternalTrack? = withContext(Dispatchers.IO) {
        val stream = contentResolver.openInputStream(uri) ?: error("File not found: $uri")
        val file = File.createTempFile("audio", ".mp3")
        val outputStream = FileOutputStream(file)
        stream.use {
            stream.copyTo(outputStream)
        }
        val audioFile = AudioFileIO.read(file)
        val tag = audioFile.tag

        return@withContext ExternalTrack(
            uri = uri,
            file = file,
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

    override suspend fun saveExternalTrack(track: ExternalTrack) {

    }
}