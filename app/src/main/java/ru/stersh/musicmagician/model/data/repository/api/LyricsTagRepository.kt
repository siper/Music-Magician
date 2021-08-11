package ru.stersh.musicmagician.model.data.repository.api

import io.reactivex.Single
import ru.stersh.musicmagician.entity.tag.LyricsTag

abstract class LyricsTagRepository : BaseRepository() {
    abstract fun getTags(title: String, artist: String): Single<List<LyricsTag>>
}