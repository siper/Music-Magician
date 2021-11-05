package ru.stersh.musicmagician.model.data.repository.app

import android.content.SharedPreferences
import ru.stersh.musicmagician.R

class UserRepository(private val preferences: SharedPreferences) {
    companion object {
        private const val HIDE_SHORT_TRACKS = "hide_short_tracks"
        private const val CURRENT_SCREEN = "current_screen"
    }

    var hideShortTracks
        get() = preferences.getBoolean(HIDE_SHORT_TRACKS, true)
        set(value) = preferences.edit().putBoolean(HIDE_SHORT_TRACKS, value).apply()

    var currentScreen
        get() = preferences.getInt(CURRENT_SCREEN, 0)
        set(value) = preferences.edit().putInt(CURRENT_SCREEN, value).apply()
}