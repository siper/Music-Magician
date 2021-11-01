package ru.stersh.musicmagician.feature.library.core

import kotlinx.coroutines.flow.*
import java.util.*

abstract class LibraryInteractor<T> {
    private val query = MutableStateFlow("")
    private val sortOrder = MutableStateFlow(1)
    private val content = MutableStateFlow<List<T>>(listOf())
    private val contentCopy = mutableListOf<T>()

    fun getContent(): Flow<List<T>> {
        return combine(
            dataSource(),
            query,
            sortOrder
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

    fun sort(order: Int) {
        sortOrder.value = order
    }

    abstract fun dataSource(): Flow<List<T>>
    abstract fun getSearchPredicate(item: T, query: String): Boolean
    abstract fun getSortComparator(sortOrder: Int): Comparator<T>
}