package ru.stersh.musicmagician.feature.library.track.data

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.stersh.musicmagician.feature.library.track.data.sortorder.TrackSortOrder
import ru.stersh.musicmagician.feature.library.track.data.sortorder.TrackSortOrderRepository

class TrackSortOrderRepositoryImpl(private val preferences: SharedPreferences) : TrackSortOrderRepository {

    override fun getSortOrder(): Flow<TrackSortOrder> = callbackFlow {
        val callback = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key != TRACK_SORT_ORDER) {
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

    private suspend fun getSortOrderFromPrefs(): TrackSortOrder = withContext(Dispatchers.IO) {
        return@withContext preferences.getString(TRACK_SORT_ORDER, null)?.run {
            TrackSortOrder.valueOf(this)
        } ?: TrackSortOrder.AZ_TITLE
    }

    @SuppressLint("ApplySharedPref")
    override suspend fun saveSortOrder(order: TrackSortOrder) {
        withContext(Dispatchers.IO) {
            preferences
                .edit()
                .putString(TRACK_SORT_ORDER, order.toString())
                .commit()
        }
    }

    companion object {
        private const val TRACK_SORT_ORDER = "sort_order_track"
    }
}