package ru.stersh.musicmagician.model.data.repository.api

abstract class BaseRepository {
    open val providerName: String = this.javaClass.simpleName.replace(Regex("Repository|TrackTagRepository|AlbumTagRepository"), "")
}