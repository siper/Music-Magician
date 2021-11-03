package ru.stersh.musicmagician.feature.library.core

import kotlinx.coroutines.flow.*
import java.util.*

abstract class LibraryInteractor<T, S> {
    private val query = MutableStateFlow("")

    fun getContent(): Flow<List<T>> {
        return combine(
            dataSource(),
            query,
            sortOrder()
        ) { content, query, sortOrder ->
            return@combine content.filter {
                return@filter if (query.isEmpty()) {
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

    abstract suspend fun sort(order: S)
    abstract fun dataSource(): Flow<List<T>>
    abstract fun sortOrder(): Flow<S>
    abstract fun getSearchPredicate(item: T, query: String): Boolean
    abstract fun getSortComparator(sortOrder: S): Comparator<T>
}