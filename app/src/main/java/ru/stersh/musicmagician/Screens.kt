package ru.stersh.musicmagician

import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.stersh.musicmagician.ui.fragment.PrivacyPolicyFragment
import ru.stersh.musicmagician.ui.fragment.editor.album.AlbumEditorFragment
import ru.stersh.musicmagician.ui.fragment.editor.track.TrackEditorFragment
import ru.stersh.musicmagician.feature.library.album.AlbumLibraryFragment
import ru.stersh.musicmagician.feature.library.track.TrackLibraryFragment

object Screens {
    fun trackLibraryScreen() = FragmentScreen {
        ru.stersh.musicmagician.feature.library.track.TrackLibraryFragment()
    }

    fun albumLibraryScreen() = FragmentScreen {
        ru.stersh.musicmagician.feature.library.album.AlbumLibraryFragment()
    }

    fun piracyPolicy() = FragmentScreen {
        PrivacyPolicyFragment()
    }

    fun translate() = ActivityScreen {
        Intent(Intent.ACTION_VIEW).apply {
            data = translateUrl
        }
    }

    fun albumEditor(album: ru.stersh.musicmagician.data.core.entity.Album) = FragmentScreen {
        AlbumEditorFragment.edit(album.id)
    }

    fun trackEditor(track: ru.stersh.musicmagician.data.core.entity.Track) = FragmentScreen {
        TrackEditorFragment.edit(track)
    }

    fun feedback() = ActivityScreen {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "MusicMagician - Music tag editor")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(feedbackEmail))
        }
        Intent.createChooser(intent, it.getString(R.string.leave_feedback))
    }
}