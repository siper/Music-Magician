package ru.stersh.musicmagician.model.interactor.library

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

abstract class LibraryInteractor<T> {
    private val lifecycle = CompositeDisposable()
    private val content = BehaviorSubject.create<List<T>>()
    private val contentCopy = mutableListOf<T>()
    private var sortOrder: Int = 0
    private var query: String = ""

    fun getContent(): Flowable<List<T>> {
        return content
                .toFlowable(BackpressureStrategy.BUFFER)
                .filter { it != null }
                .map {
                    it
                            .filter {
                                return@filter if (query.isNotEmpty()) {
                                    getSearchPredicate(it, query)
                                } else {
                                    true
                                }
                            }
                            .sortedWith(getSortComparator(sortOrder))
                            .toList()
                }
                .doOnSubscribe {
                    dataSource()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    { data ->
                                        if (data.isNotEmpty()) {
                                            contentCopy.apply {
                                                clear()
                                                addAll(data)
                                            }
                                            content.onNext(data)
                                        }
                                    },
                                    {
                                        Timber.e(it)
                                    }
                            ).addTo(lifecycle)
                }
                .doFinally { lifecycle.clear() }
                .skip(1)
    }


    fun search(query: String) {
        this.query = query.toLowerCase()
        content.onNext(contentCopy)
    }

    fun sort(order: Int) {
        this.sortOrder = order
        content.onNext(contentCopy)
    }

    abstract fun dataSource(): Flowable<List<T>>
    abstract fun getSearchPredicate(item: T, query: String): Boolean
    abstract fun getSortComparator(sortOrder: Int): Comparator<T>
}