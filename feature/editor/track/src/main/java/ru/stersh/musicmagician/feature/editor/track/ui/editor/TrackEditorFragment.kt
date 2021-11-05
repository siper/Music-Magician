package ru.stersh.musicmagician.feature.editor.track.ui.editor

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import kotlinx.coroutines.flow.collect
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import ru.stersh.musicmagician.feature.editor.core.ui.EditorFragment
import ru.stersh.musicmagician.feature.editor.track.R
import ru.stersh.musicmagician.feature.editor.track.ui.tageditor.TrackTagEditorFragment
import ru.stersh.musicmagician.feature.editor.track.ui.tagsearch.TrackTagSearchFragment

class TrackEditorFragment : EditorFragment() {
    private val id: Long by lazy { requireArguments().getLong(ID) }
    private val viewModel: TrackEditorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.edit(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.exit.collect {
                Toast.makeText(requireContext(), R.string.file_not_found, Toast.LENGTH_SHORT).show()
                router.exit()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.title.collect {
                binding.title.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.artist.collect {
                binding.subtitle.text = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.albumArt.collect {
                binding.albumart.load(it)
            }
        }
    }

    override fun saveTags() {
        viewModel.save()
    }

    override fun getEditor(): Fragment = TrackTagEditorFragment()

    override fun getSearch(): Fragment = TrackTagSearchFragment()

    companion object {
        private const val ID = "id"

        fun edit(id: Long): TrackEditorFragment {
            val bundle = Bundle().apply {
                putLong(ID, id)
            }
            return TrackEditorFragment().apply {
                arguments = bundle
            }
        }
    }
}