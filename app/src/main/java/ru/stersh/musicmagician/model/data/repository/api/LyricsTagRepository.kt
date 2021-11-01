package ru.stersh.musicmagician.model.data.repository.api

import io.reactivex.Single
import ru.stersh.musicmagician.data.server.core.entity.LyricsTag

abstract class LyricsTagRepository : BaseRepository() {
    abstract fun getTags(title: String, artist: String): Single<List<ru.stersh.musicmagician.data.server.core.entity.LyricsTag>>
}