package ru.stersh.musicmagician.model.interactor.search

import io.reactivex.Single
import io.reactivex.functions.BiFunction
import ru.stersh.musicmagician.data.server.core.entity.AlbumTag
import ru.stersh.musicmagician.data.server.core.entity.Tag
import ru.stersh.musicmagician.data.server.core.entity.TagEntity
import ru.stersh.musicmagician.model.data.repository.api.deezer.DeezerAlbumTagRepository
import ru.stersh.musicmagician.model.data.repository.api.itunes.ItunesAlbumTagRepository

class AlbumSearchInteractor(
    private val deezerAlbumTagRepository: DeezerAlbumTagRepository,
    private val itunesAlbumTagRepository: ItunesAlbumTagRepository
) {
    fun searchTags(
        title: String,
        artist: String
    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.TagEntity>> {
        return Single.zip(
            deezer(title, artist),
            itunes(title, artist),
            BiFunction { t1, t2 ->
                mutableListOf<ru.stersh.musicmagician.data.server.core.entity.Tag>().apply {
                    addAll(t1)
                    addAll(t2)
                    sortByDescending { it.priority }
                    toList()
                }
            }
        )
    }

    private fun deezer(
        title: String,
        artist: String
    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.AlbumTag>> {
        return deezerAlbumTagRepository
            .getTags(title, artist)
            .onErrorReturn { emptyList() }
    }

    private fun itunes(
        title: String,
        artist: String
    ): Single<List<ru.stersh.musicmagician.data.server.core.entity.AlbumTag>> {
        return itunesAlbumTagRepository
            .getTags(title, artist)
            .onErrorReturn { emptyList() }
    }
}