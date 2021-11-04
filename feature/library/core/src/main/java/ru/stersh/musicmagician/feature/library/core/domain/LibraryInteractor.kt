package ru.stersh.musicmagician.feature.library.core.domain

import kotlinx.coroutines.flow.*
import java.util.*

abstract class LibraryInteractor<T, S> {
    private val query = MutableStateFlow("")

    fun getContent(): Flow<List<T>> {
        return combine(dataSource(), query, sortOrder()) { content, query, sortOrder ->
            content.filter {
                if (query.isEmpty()) {
                    true
                } else {
                    getSearchPredicate(it, query)
                }
            }.sortedWith(getSortComparator(sortOrder))
        }
    }

    fun search(query: String) {
        this.query.value = query.lowercase()
    }

    abstract fun sortOrder(): Flow<S>
    abstract suspend fun sort(order: S)

    protected abstract fun dataSource(): Flow<List<T>>
    protected abstract fun getSearchPredicate(item: T, query: String): Boolean
    protected abstract fun getSortComparator(sortOrder: S): Comparator<T>
}