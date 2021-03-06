package ru.stersh.musicmagician.presentation.presenter.editor.album

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.stersh.musicmagician.entity.mediastore.Album
import ru.stersh.musicmagician.entity.tag.AlbumTag
import ru.stersh.musicmagician.entity.tag.Tag
import ru.stersh.musicmagician.entity.tag.TagEntity
import ru.stersh.musicmagician.model.data.repository.media.AlbumRepository
import ru.stersh.musicmagician.model.interactor.search.AlbumSearchInteractor
import ru.stersh.musicmagician.presentation.presenter.BasePresenter
import ru.stersh.musicmagician.presentation.view.editor.TagSearchView
import ru.stersh.musicmagician.tempAlbumart
import ru.stersh.musicmagician.utils.FileDownloader
import timber.log.Timber

@InjectViewState
class AlbumTagSearchPresenter(
        private val repository: AlbumRepository,
        private val interactor: AlbumSearchInteractor,
        private val downloader: FileDownloader,
        private val id: Long
) : BasePresenter<TagSearchView>() {
    private lateinit var album: Album
    private var isUpdates = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        repository
                .getAlbum(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it != null) {
                                if (!isUpdates) {
                                    album = it
                                    search(it)
                                }
                                isUpdates = false
                            }
                        },
                        {
                            Timber.e(it)
                        }
                ).addTo(presenterLifecycle)
    }

    fun applyTag(tag: Tag) {
        if (tag is AlbumTag) {
            downloader
                    .download(tag.albumart, tempAlbumart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onComplete = {
                                isUpdates = true
                                val newAlbum = album.copy(
                                        title = tag.album,
                                        artist = tag.artist,
                                        albumart = tempAlbumart,
                                        year = if (tag.year != 0) tag.year.toString() else ""
                                )
                                repository.updateAlbum(newAlbum)
                            },
                            onError = {
                                Timber.e(it)
                            }
                    )
                    .addTo(presenterLifecycle)
        }
    }

    private fun search(album: Album) {
        interactor
                .searchTags(album.title, album.artist)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    viewState.showProgress()
                }
                .subscribe(
                        { tags: List<TagEntity> ->
                            if (tags.isEmpty()) {
                                viewState.showStub()
                            } else {
                                viewState.showContent(tags)
                            }
                        },
                        {
                            Timber.e(it)
                            viewState.showStub()
                        }
                ).addTo(presenterLifecycle)
    }
}