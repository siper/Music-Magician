package ru.stersh.musicmagician.data.core.external.entity

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

data class ExternalTrack(
    val uri: Uri,
    val file: File,
    val title: String?,
    val artist: String?,
    val album: String?,
    val albumArt: Bitmap?,
    val trackNumber: String?,
    val comment: String?,
    val year: String?,
    val genre: String?,
    val lyrics: String?
)