//package ru.stersh.musicmagician.presentation.presenter.editor.album
//
//import com.github.terrakok.cicerone.Router
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.rxkotlin.addTo
//import io.reactivex.schedulers.Schedulers
//import moxy.InjectViewState
//import ru.stersh.musicmagician.model.data.repository.media.AlbumRepository
//import ru.stersh.musicmagician.presentation.presenter.BasePresenter
//import ru.stersh.musicmagician.presentation.view.editor.EditorView
//import timber.log.Timber
//
//@InjectViewState
//class AlbumEditorPresenter(
//    private val repository: AlbumRepository,
//    private val router: Router,
//    private val id: Long
//) : BasePresenter<EditorView>() {
//    private var album: Album? = null
//
//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        repository
//            .getAlbum(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    album = it
//                    viewState.bindHeader(it.title, it.artist, it.albumart)
//                },
//                {
//                    router.exit()
//                    Timber.e(it)
//                }
//            )
//            .addTo(presenterLifecycle)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        album?.let {
//            repository.clearCache(it)
//        }
//    }
//
//    fun save() {
//        repository
//            .save(album)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    router.exit()
//                },
//                {
//                    Timber.e(it)
//                }
//            )
//            .addTo(presenterLifecycle)
//    }
//}