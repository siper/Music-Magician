package ru.stersh.musicmagician.entity.tag

data class LyricsTag(
        val lyrics: String,
        override val provider: String,
        override val priority: Int = -10
) : Tag