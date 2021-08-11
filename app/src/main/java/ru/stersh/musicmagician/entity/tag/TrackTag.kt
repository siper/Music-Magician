package ru.stersh.musicmagician.entity.tag

data class TrackTag(
        val title: String = "",
        val artist: String = "",
        val album: String = "",
        val albumart: String = "",
        val genre: String = "",
        val year: Int = 0,
        val number: Int = 0,
        override val provider: String,
        override val priority: Int = 0
) : Tag {
    fun calculatePriority(query: String): TrackTag {
        var p = 0
        val lQuery = query.toLowerCase()
        if (title.isNotEmpty() && title.isNotBlank()) {
            p++
            if (query.contains(title)) p += 3
            if (lQuery.contains(title)) p += 3
        }
        Stopwords.TRACK_TITLE.forEach {
            if (title.contains(it)) p--
        }
        if (artist.isNotEmpty() && artist.isNotBlank()) {
            p++
            if (query.contains(artist)) p += 3
            if (lQuery.contains(artist)) p += 3
        }
        if (album.isNotEmpty() && album.isNotBlank()) {
            p++
        }
        Stopwords.TRACK_ALBUM.forEach {
            if (album.contains(it)) p--
        }
        if (albumart.isNotEmpty() && albumart.isNotBlank()) {
            p++
        }
        if (genre.isNotEmpty() && genre.isNotBlank()) {
            p++
        }
        if (year > 0) {
            p++
        }
        if (number > 0) {
            p++
        }
        return this.copy(priority = p)
    }
}