package ru.stersh.musicmagician.entity.tag

object Stopwords {
    val TRACK_TITLE = listOf("Album", "Live")
    val TRACK_ALBUM =
        listOf("Single", "Live", "Remastered", "Deluxe", "Bonus", "Special", "Expanded Edition", "Platinum Edition")

    val ALBUM_TITLE = TRACK_ALBUM
}