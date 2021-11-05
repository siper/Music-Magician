package ru.stersh.musicmagician.feature.editor.track.data

import android.graphics.Bitmap
import android.net.Uri

data class EditableTrack(
    val id: Long,
    val uri: Uri,
    val albumId: Long,
    val title: String,
    val artist: String,
    val album: String,
    val albumArt: Bitmap?,
    val trackNumber: String,
    val comment: String,
    val year: String,
    val genre: String,
    val lyrics: String
)