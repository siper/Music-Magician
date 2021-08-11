package ru.stersh.musicmagician.model.data.repository.api

import io.reactivex.Single
import ru.stersh.musicmagician.entity.tag.AlbumTag

abstract class AlbumTagRepository : BaseRepository() {
    abstract fun getTags(album: String, artist: String): Single<List<AlbumTag>>
}