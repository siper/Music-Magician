package ru.stersh.musicmagician.feature.library.album.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stersh.musicmagician.feature.library.album.data.sortorder.AlbumSortOrder
import ru.stersh.musicmagician.feature.library.album.data.sortorder.AlbumSortOrderRepository

class AlbumSortOrderRepositoryImpl(private val preferences: SharedPreferences) : AlbumSortOrderRepository {
    override fun getSortOrder(): Flow<AlbumSortOrder> = callbackFlow {
        val callback = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key != ALBUM_SORT_ORDER) {
                return@OnSharedPreferenceChangeListener
            }
            launch {
                send(getSortOrderFromPrefs())
            }
        }
        preferences.registerOnSharedPreferenceChangeListener(callback)

        send(getSortOrderFromPrefs())

        awaitClose {
            preferences.unregisterOnSharedPreferenceChangeListener(callback)
        }
    }

    private suspend fun getSortOrderFromPrefs(): AlbumSortOrder = withContext(Dispatchers.IO) {
        return@withContext preferences.getString(ALBUM_SORT_ORDER, null)?.run {
            AlbumSortOrder.valueOf(this)
        } ?: AlbumSortOrder.AZ_TITLE
    }

    @SuppressLint("ApplySharedPref")
    override suspend fun saveSortOrder(order: AlbumSortOrder) {
        withContext(Dispatchers.IO) {
            preferences
                .edit()
                .putString(ALBUM_SORT_ORDER, order.toString())
                .commit()
        }
    }

    companion object {
        private const val ALBUM_SORT_ORDER = "sort_order_album"
    }
}