//package ru.stersh.musicmagician.presentation.presenter.main
//
//import android.Manifest
//import com.github.terrakok.cicerone.Router
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.rxkotlin.addTo
//import io.reactivex.schedulers.Schedulers
//import moxy.InjectViewState
//import ru.stersh.musicmagician.Screens
//import ru.stersh.musicmagician.model.data.repository.app.PermissionsRepository
//import ru.stersh.musicmagician.model.data.repository.app.UserRepository
//import ru.stersh.musicmagician.presentation.presenter.BasePresenter
//import ru.stersh.musicmagician.presentation.view.main.MainView
//import timber.log.Timber
//
//@InjectViewState
//class MainPresenter(
//    private val permissions: PermissionsRepository,
//    private val preferences: UserRepository,
//    private val router: Router
//) : BasePresenter<MainView>() {
//    override fun onFirstViewAttach() {
//        permissions
//            .hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .distinctUntilChanged()
//            .subscribe(
//                {
//                    if (it) {
//                        when (preferences.currentScreen) {
//                            0 -> {
//                                router.newRootScreen(Screens.trackLibraryScreen())
//                                viewState.trackLibrary()
//                            }
//                            1 -> {
//                                router.newRootScreen(Screens.albumLibraryScreen())
//                                viewState.albumLibrary()
//                            }
//                        }
//                    } else {
//                        viewState.showPermissionsError()
//                    }
//                },
//                Timber::e
//            )
//            .addTo(presenterLifecycle)
//    }
//
//    fun requestPermissions() = permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//
//    fun trackLibrary() {
//        preferences.currentScreen = 0
//        permissions
//            .hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .firstElement()
//            .subscribe(
//                {
//                    if (it) router.newRootScreen(Screens.trackLibraryScreen())
//                },
//                Timber::e
//            )
//            .addTo(presenterLifecycle)
//    }
//
//    fun albumLibrary() {
//        preferences.currentScreen = 1
//        permissions
//            .hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .firstElement()
//            .subscribe(
//                {
//                    if (it) router.newRootScreen(Screens.albumLibraryScreen())
//                },
//                Timber::e
//            )
//            .addTo(presenterLifecycle)
//    }
//
//    fun feedback() = router.navigateTo(Screens.feedback())
//
//    fun privacyPolicy() = router.navigateTo(Screens.piracyPolicy())
//
//    fun translate() = router.navigateTo(Screens.translate())
//}