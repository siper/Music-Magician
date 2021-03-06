package ru.stersh.musicmagician.presentation.presenter.editor.track

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.stersh.musicmagician.entity.mediastore.Track
import ru.stersh.musicmagician.entity.tag.LyricsTag
import ru.stersh.musicmagician.entity.tag.Tag
import ru.stersh.musicmagician.entity.tag.TagEntity
import ru.stersh.musicmagician.entity.tag.TrackTag
import ru.stersh.musicmagician.model.data.repository.media.TrackRepository
import ru.stersh.musicmagician.model.interactor.search.TrackSearchInteractor
import ru.stersh.musicmagician.presentation.presenter.BasePresenter
import ru.stersh.musicmagician.presentation.view.editor.TagSearchView
import ru.stersh.musicmagician.tempAlbumart
import ru.stersh.musicmagician.utils.FileDownloader
import timber.log.Timber

@InjectViewState
class TrackTagSearchPresenter(
        private val repository: TrackRepository,
        private val interactor: TrackSearchInteractor,
        private val downloader: FileDownloader,
        private val path: String
) : BasePresenter<TagSearchView>() {
    private lateinit var track: Track
    private var isUpdates = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        repository
                .getTrack(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it != null) {
                                if (!isUpdates) {
                                    track = it
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
        if (tag is TrackTag) {
            downloader
                    .download(tag.albumart, tempAlbumart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy(
                            onComplete = {
                                isUpdates = true
                                val oldTrack = track.copy(
                                        title = tag.title,
                                        artist = tag.artist,
                                        album = tag.album,
                                        albumart = tempAlbumart,
                                        trackNumber = if (tag.number != 0) tag.number.toString() else "",
                                        year = if (tag.year != 0) tag.year.toString() else ""
                                )
                                repository.updateTrack(oldTrack)
                            },
                            onError = {
                                Timber.e(it)
                            }
                    )
                    .addTo(presenterLifecycle)
        }
        if (tag is LyricsTag) {
            if (tag.lyrics.isNotEmpty()) {
                isUpdates = true
                repository.updateTrack(track.copy(lyrics = tag.lyrics))
            }
        }
    }

    private fun search(track: Track) {
        interactor
                .searchTags(track.title, track.artist)
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