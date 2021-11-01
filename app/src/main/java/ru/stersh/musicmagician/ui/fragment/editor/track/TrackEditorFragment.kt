package ru.stersh.musicmagician.ui.fragment.editor.track

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import moxy.ktx.moxyPresenter
import org.koin.core.get
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.di.Di
import ru.stersh.musicmagician.data.core.entity.Track
import ru.stersh.musicmagician.presentation.presenter.editor.track.TrackEditorPresenter
import ru.stersh.musicmagician.ui.fragment.editor.EditorFragment
import java.io.File

class TrackEditorFragment : EditorFragment() {
    private val path by lazy { requireArguments().getString(PATH_KEY)!! }
    private val presenter by moxyPresenter {
        TrackEditorPresenter(Di.get(), Di.get(), path)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (path.isNullOrEmpty() || !File(path).exists()) {
            Toast.makeText(requireContext(), getString(R.string.file_not_found), Toast.LENGTH_SHORT).show()
            router.exit()
        }
    }

    override fun saveTags() = presenter.save()

    override fun getEditor(): Fragment = TrackTagEditorFragment.path(path)

    override fun getSearch(): Fragment = TrackTagSearchFragment.path(path)

    companion object {
        private const val PATH_KEY = "path"
        fun edit(track: ru.stersh.musicmagician.data.core.entity.Track): TrackEditorFragment {
            val bundle = Bundle().apply {
                putString(PATH_KEY, track.path)
            }
            return TrackEditorFragment().apply {
                arguments = bundle
            }
        }
    }
}