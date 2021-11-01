package ru.stersh.musicmagician.model.data.repository.api

import io.reactivex.Single
import ru.stersh.musicmagician.data.server.core.entity.AlbumTag

abstract class AlbumTagRepository : BaseRepository() {
    abstract fun getTags(album: String, artist: String): Single<List<ru.stersh.musicmagician.data.server.core.entity.AlbumTag>>
}