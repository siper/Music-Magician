package ru.stersh.musicmagician.entity.mediastore

import android.net.Uri

data class Track(
        val id: Long = 0,
        val path: String,
        val title: String,
        val dateAdded: Long = 0,
        val dateModified: Long = 0,
        val artist: String,
        val album: String,
        val trackNumber: String,
        val year: String,
        val uri: Uri? = null,
        val albumId: Long = 0,
        val duration: Long = 0,
        val genre: String = "",
        val lyrics: String = "",
        val albumart: String = "",
        val comment: String = ""
) : MediastoreItem