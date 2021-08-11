package ru.stersh.musicmagician.presentation.presenter.library

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import ru.stersh.musicmagician.entity.app.ui.AlbumSortOrder
import ru.stersh.musicmagician.model.data.repository.app.UserRepository
import ru.stersh.musicmagician.model.interactor.library.AlbumLibraryInteractor
import ru.stersh.musicmagician.presentation.presenter.BasePresenter
import ru.stersh.musicmagician.presentation.view.library.LibraryView
import timber.log.Timber

@InjectViewState
class AlbumLibraryPresenter(
        private val interactor: AlbumLibraryInteractor,
        private val preferences: UserRepository
) : BasePresenter<LibraryView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        interactor.sort(preferences.albumSortOrder)
        interactor
                .getContent()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            if (it.isNotEmpty()) {
                                viewState.showContent(it)
                            } else {
                                viewState.showStub()
                            }
                        },
                        {
                            Timber.e(it)
                            viewState.showStub()
                        }
                ).addTo(presenterLifecycle)
    }

    fun getSortOrder() = preferences.albumSortOrder

    fun search(query: String) = interactor.search(query)

    fun sort(order: AlbumSortOrder) {
        preferences.albumSortOrder = order.order
        viewState.setSortOrder(order.order)
        interactor.sort(order.order)
    }
}