//package ru.stersh.musicmagician.presentation.presenter.editor.track
//
//import com.github.terrakok.cicerone.Router
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.rxkotlin.addTo
//import io.reactivex.schedulers.Schedulers
//import moxy.InjectViewState
//import ru.stersh.musicmagician.model.data.repository.media.TrackRepository
//import ru.stersh.musicmagician.presentation.presenter.BasePresenter
//import ru.stersh.musicmagician.presentation.view.editor.EditorView
//import timber.log.Timber
//
//@InjectViewState
//class TrackEditorPresenter(
//    private val repository: TrackRepository,
//    private val router: Router,
//    private val path: String
//) : BasePresenter<EditorView>() {
//    private var track: Track? = null
//
//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        repository
//            .getTrack(path)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    track = it
//                    viewState.bindHeader(it.title, it.artist, it.albumart)
//                },
//                {
//                    Timber.e(it)
//                }
//            )
//            .addTo(presenterLifecycle)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        track?.let {
//            repository.clearCache(it)
//        }
//    }
//
//    fun save() {
//        repository
//            .save(track)
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