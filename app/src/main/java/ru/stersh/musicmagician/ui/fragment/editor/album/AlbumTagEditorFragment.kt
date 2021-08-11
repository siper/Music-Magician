package ru.stersh.musicmagician.ui.fragment.editor.album

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.databinding.FragmentAlbumEditorBinding
import ru.stersh.musicmagician.databinding.FragmentBaseEditorBinding
import ru.stersh.musicmagician.entity.mediastore.Album
import ru.stersh.musicmagician.model.data.repository.media.AlbumRepository
import ru.stersh.musicmagician.utils.android.EmptyTextWatcher
import timber.log.Timber

class AlbumTagEditorFragment : Fragment() {
    private val lifecycle = CompositeDisposable()
    private val repository by inject<AlbumRepository>()
    private val albumId by lazy { requireArguments().getLong(AlbumEditorFragment.ALBUM_ID) }
    private var album: Album? = null

    private val titleTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            album?.let {
                repository.save(it.copy(title = binding.albumEditorTitle.text.toString()))
            }
        }
    }
    private val artistTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            album?.let {
                repository.updateAlbum(it.copy(artist = binding.albumEditorArtist.text.toString()))
            }
        }
    }
    private val yearTextWatcher: EmptyTextWatcher = object : EmptyTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            album?.let {
                repository.updateAlbum(it.copy(year = binding.albumEditorYear.text.toString()))
            }
        }
    }

    private var _binding: FragmentAlbumEditorBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        refreshData()
    }

    override fun onStop() {
        super.onStop()
        lifecycle.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.dispose()
    }

    private fun refreshData() {
        repository
                .getAlbum(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { album: Album ->
                            this.album = album
                            disableEditor()
                            if (binding.albumEditorTitle.text.toString() != album.title) {
                                binding.albumEditorTitle.setText(album.title)
                            }
                            if (binding.albumEditorArtist.text.toString() != album.artist) {
                                binding.albumEditorArtist.setText(album.artist)
                            }
                            if (binding.albumEditorYear.text.toString() != album.year) {
                                binding.albumEditorYear.setText(album.year)
                            }
                            enableEditor()
                        },
                        {
                            Timber.e(it)
                        }
                )
                .addTo(lifecycle)
    }

    private fun enableEditor() {
        binding.albumEditorTitle.addTextChangedListener(titleTextWatcher)
        binding.albumEditorArtist.addTextChangedListener(artistTextWatcher)
        binding.albumEditorYear.addTextChangedListener(yearTextWatcher)
    }

    private fun disableEditor() {
        binding.albumEditorTitle.removeTextChangedListener(titleTextWatcher)
        binding.albumEditorArtist.removeTextChangedListener(artistTextWatcher)
        binding.albumEditorYear.removeTextChangedListener(yearTextWatcher)
    }

    companion object {
        fun albumId(id: Long?): AlbumTagEditorFragment {
            val bundle = Bundle()
            bundle.putLong(AlbumEditorFragment.ALBUM_ID, id!!)
            val fr = AlbumTagEditorFragment()
            fr.arguments = bundle
            return fr
        }
    }
}