package ru.stersh.musicmagician.feature.editor.track.ui.tageditor

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.stersh.musicmagician.feature.editor.core.ui.tageditor.EmptyTextWatcher
import ru.stersh.musicmagician.feature.editor.track.databinding.FragmentTrackEditorBinding

class TrackTagEditorFragment : Fragment() {
    private val viewModel: TrackTagEditorViewModel by viewModel()

    private val titleTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateTitle(binding.title.text.toString())
        }
    }
    private val albumTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateAlbum(binding.album.text.toString())
        }
    }
    private val artistTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateArtist(binding.artist.text.toString())
        }
    }
    private val commentTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateComment(binding.comment.text.toString())
        }
    }
    private val yearTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateYear(binding.year.text.toString())
        }
    }
    private val trackNumberTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateTrackNumber(binding.trackNumber.text.toString())
        }
    }
    private val genreTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateGenre(binding.genre.text.toString())
        }
    }
    private val lyricsTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            viewModel.updateLyrics(binding.lyrics.text.toString())
        }
    }

    private var _binding: FragmentTrackEditorBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeFields()
    }

    private fun subscribeFields() {
        lifecycleScope.launchWhenStarted {
            viewModel.title.collect {
                if (binding.title.text?.toString() != it) {
                    binding.title.removeTextChangedListener(titleTextWatcher)
                    binding.title.setText(it)
                    binding.title.addTextChangedListener(titleTextWatcher)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.album.collect {
                if (binding.album.text?.toString() != it) {
                    binding.album.removeTextChangedListener(albumTextWatcher)
                    binding.album.setText(it)
                    binding.album.addTextChangedListener(albumTextWatcher)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.artist.collect {
                if (binding.artist.text?.toString() != it) {
                    binding.artist.removeTextChangedListener(artistTextWatcher)
                    binding.artist.setText(it)
                    binding.artist.addTextChangedListener(artistTextWatcher)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.comment.collect {
                if (binding.comment.text?.toString() != it) {
                    binding.comment.removeTextChangedListener(commentTextWatcher)
                    binding.comment.setText(it)
                    binding.comment.addTextChangedListener(commentTextWatcher)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.year.collect {
                if (binding.year.text?.toString() != it) {
                    binding.year.removeTextChangedListener(yearTextWatcher)
                    binding.year.setText(it)
                    binding.year.addTextChangedListener(yearTextWatcher)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.trackNumber.collect {
                if (binding.trackNumber.text?.toString() != it) {
                    binding.trackNumber.removeTextChangedListener(trackNumberTextWatcher)
                    binding.trackNumber.setText(it)
                    binding.trackNumber.addTextChangedListener(trackNumberTextWatcher)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.genre.collect {
                if (binding.genre.text?.toString() != it) {
                    binding.genre.removeTextChangedListener(genreTextWatcher)
                    binding.genre.setText(it)
                    binding.genre.addTextChangedListener(genreTextWatcher)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.lyrics.collect {
                if (binding.lyrics.text?.toString() != it) {
                    binding.lyrics.removeTextChangedListener(lyricsTextWatcher)
                    binding.lyrics.setText(it)
                    binding.lyrics.addTextChangedListener(lyricsTextWatcher)
                }
            }
        }
    }
}