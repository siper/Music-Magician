package ru.stersh.musicmagician.entity.mediastore

data class Album(
        val id: Long,
        val title: String,
        val artist: String,
        val albumart: String,
        val year: String
) : MediastoreItem