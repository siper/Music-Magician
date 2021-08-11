package ru.stersh.musicmagician.model.data.repository.app

import android.content.SharedPreferences
import ru.stersh.musicmagician.R

class UserRepository(private val preferences: SharedPreferences) {
    companion object {
        private const val TRACK_SORT_ORDER = "track_sort_order"
        private const val ALBUM_SORT_ORDER = "album_sort_order"
        private const val HIDE_SHORT_TRACKS = "hide_short_tracks"
        private const val CURRENT_SCREEN = "current_screen"
    }

    var trackSortOrder
        get() = preferences.getInt(TRACK_SORT_ORDER, R.id.az_title_sort)
        set(value) = preferences.edit().putInt(TRACK_SORT_ORDER, value).apply()

    var albumSortOrder
        get() = preferences.getInt(ALBUM_SORT_ORDER, R.id.az_title_sort)
        set(value) = preferences.edit().putInt(ALBUM_SORT_ORDER, value).apply()

    var hideShortTracks
        get() = preferences.getBoolean(HIDE_SHORT_TRACKS, true)
        set(value) = preferences.edit().putBoolean(HIDE_SHORT_TRACKS, value).apply()

    var currentScreen
        get() = preferences.getInt(CURRENT_SCREEN, 0)
        set(value) = preferences.edit().putInt(CURRENT_SCREEN, value).apply()
}