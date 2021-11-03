package ru.stersh.musicmagician.feature.library.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class LibraryViewModel<T, S> : ViewModel() {
    abstract val items: Flow<List<T>>
    abstract val sortOrder: Flow<S>

    abstract fun sort(order: S)
    abstract fun search(query: String)
}