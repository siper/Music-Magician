package ru.stersh.musicmagician.feature.library.track.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.stersh.musicmagician.core.navigation.DrawerNavigation
import ru.stersh.musicmagician.feature.library.core.databinding.FragmentLibraryBinding
import ru.stersh.musicmagician.feature.library.track.R
import ru.stersh.musicmagician.feature.library.track.navigation.TrackLibraryNavigation
import ru.stersh.musicmagician.feature.library.track.ui.entity.UiItem
import ru.stersh.musicmagician.feature.library.track.ui.entity.UiTrackSortOrder
import ru.stersh.musicmagician.ui.extension.dp
import ru.stersh.musicmagician.ui.extension.gone
import ru.stersh.musicmagician.ui.extension.show

class TrackLibraryFragment : Fragment(), SearchView.OnQueryTextListener {
    private val navigation: TrackLibraryNavigation by inject()
    private val viewModel: TrackLibraryViewModel by viewModel()

    private val adapter = TrackLibraryAdapter { track, _ ->
        navigation.openTrackEditor(track.id)
    }

    private var _binding: FragmentLibraryBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initContent()

        subscribeContent()
        subscribeSortOrder()
        subscribeItems()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) {
            viewModel.search("")
        } else {
            viewModel.search(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null) {
            viewModel.search("")
        } else {
            viewModel.search(newText)
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.content.adapter = null
        _binding = null
    }

    private fun showContent(items: List<UiItem>) {
        adapter.items = items
        binding.content.show()
        binding.stub.root.gone()
    }

    private fun showStub() {
        binding.stub.root.show()
        binding.stub.stubTitle.text = getString(ru.stersh.musicmagician.feature.library.core.R.string.media_error_title)
        binding.stub.stubMessage.text = getString(ru.stersh.musicmagician.feature.library.core.R.string.media_error_message)
        binding.content.gone()
    }

    private fun initContent() {
        val orientation = requireActivity().resources.configuration.orientation
        val layoutManager = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager(activity).apply {
                setOrientation(LinearLayoutManager.VERTICAL)
            }
        } else {
            GridLayoutManager(activity, 2).apply {
                setOrientation(LinearLayoutManager.VERTICAL)
            }
        }
        binding.content.adapter = adapter
        binding.content.layoutManager = layoutManager
        binding.content.addItemDecoration(TopAndBottomMargin(8.dp))
        binding.content.setHasFixedSize(true)
    }

    private fun initToolbar() = with(binding.toolbar) {
        inflateMenu(R.menu.tracks_menu)

        val searchItem = binding.toolbar.menu?.findItem(R.id.search)
        val search = searchItem?.actionView as? SearchView
        search?.setOnQueryTextListener(this@TrackLibraryFragment)

        setNavigationIcon(ru.stersh.musicmagician.feature.library.core.R.drawable.ic_menu_24px)
        setNavigationOnClickListener {
            (requireActivity() as? DrawerNavigation)?.openDrawer()
        }
        setOnMenuItemClickListener { item ->
            return@setOnMenuItemClickListener when (item.itemId) {
                R.id.az_title_sort -> {
                    viewModel.sort(UiTrackSortOrder.AZ_TITLE)
                    true
                }
                R.id.za_title_sort -> {
                    viewModel.sort(UiTrackSortOrder.ZA_TITLE)
                    true
                }
                R.id.az_artist_sort -> {
                    viewModel.sort(UiTrackSortOrder.AZ_ARTIST)
                    true
                }
                R.id.za_artist_sort -> {
                    viewModel.sort(UiTrackSortOrder.ZA_ARTIST)
                    true
                }
                R.id.oldest_sort -> {
                    viewModel.sort(UiTrackSortOrder.OLDEST)
                    true
                }
                R.id.newest_sort -> {
                    viewModel.sort(UiTrackSortOrder.NEWEST)
                    true
                }
                android.R.id.home -> {
                    (requireActivity() as? DrawerNavigation)?.openDrawer()
                    true
                }
                else -> false
            }
        }

        setTitle(ru.stersh.musicmagician.ui.R.string.tracks)
    }

    private fun subscribeSortOrder() = lifecycleScope.launchWhenStarted {
        viewModel.sortOrder.collect {
            val itemId = when (it) {
                UiTrackSortOrder.AZ_TITLE -> R.id.az_title_sort
                UiTrackSortOrder.ZA_TITLE -> R.id.za_title_sort
                UiTrackSortOrder.AZ_ARTIST -> R.id.az_artist_sort
                UiTrackSortOrder.ZA_ARTIST -> R.id.za_artist_sort
                UiTrackSortOrder.NEWEST -> R.id.newest_sort
                UiTrackSortOrder.OLDEST -> R.id.oldest_sort
            }
            binding.toolbar.menu?.findItem(itemId)?.isChecked = true
        }
    }

    private fun subscribeContent() = lifecycleScope.launchWhenStarted {
        viewModel.items.collect {
            if (it.isEmpty()) {
                showStub()
            } else {
                showContent(it)
            }
        }
    }

    private fun subscribeItems() = lifecycleScope.launchWhenStarted {
        viewModel.items.collect {
            adapter.items = it
        }
    }
}