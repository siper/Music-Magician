package ru.stersh.musicmagician.feature.library.album

import moxy.InjectViewState
import ru.stersh.musicmagician.entity.app.ui.AlbumSortOrder
import ru.stersh.musicmagician.feature.library.core.LibraryView
import ru.stersh.musicmagician.model.data.repository.app.UserRepository
import ru.stersh.musicmagician.presentation.presenter.BasePresenter

@InjectViewState
class AlbumLibraryPresenter(
    private val interactor: ru.stersh.musicmagician.feature.library.album.AlbumLibraryInteractor,
    private val preferences: UserRepository
) : BasePresenter<LibraryView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgress()
        interactor.sort(preferences.albumSortOrder)
        presenterScope.launch {
            interactor.getContent().collect {
                if (it.isNotEmpty()) {
                    viewState.showContent(it)
                } else {
                    viewState.showStub()
                }
            }
        }
    }

    fun getSortOrder() = preferences.albumSortOrder

    fun search(query: String) = interactor.search(query)

    fun sort(order: AlbumSortOrder) {
        preferences.albumSortOrder = order.order
        viewState.setSortOrder(order.order)
        interactor.sort(order.order)
    }
}