package ru.stersh.musicmagician.presentation.presenter

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {
    protected val presenterLifecycle = CompositeDisposable()
    protected val viewLifecycle = CompositeDisposable()

    override fun detachView(view: T) {
        super.detachView(view)
        viewLifecycle.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterLifecycle.dispose()
        viewLifecycle.dispose()
    }
}
