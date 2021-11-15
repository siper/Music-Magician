package ru.stersh.musicmagician.feature.library.album.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.stersh.musicmagician.core.navigation.DrawerNavigation
import ru.stersh.musicmagician.feature.library.album.R
import ru.stersh.musicmagician.feature.library.album.navigation.AlbumLibraryNavigation
import ru.stersh.musicmagician.feature.library.album.ui.entity.UiAlbumSortOrder
import ru.stersh.musicmagician.feature.library.album.ui.entity.UiItem
import ru.stersh.musicmagician.feature.library.core.databinding.FragmentLibraryBinding
import ru.stersh.musicmagician.ui.extension.gone
import ru.stersh.musicmagician.ui.extension.show

class AlbumLibraryFragment : Fragment(), SearchView.OnQueryTextListener {
    private val navigation: AlbumLibraryNavigation by inject()
    private val viewModel: AlbumLibraryViewModel by viewModel()

    private val adapter = AlbumLibraryAdapter {
        navigation.openAlbumEditor(it.id)
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
            GridLayoutManager(activity, 2).apply {
                setOrientation(LinearLayoutManager.VERTICAL)
            }
        } else {
            GridLayoutManager(activity, 4).apply {
                setOrientation(LinearLayoutManager.VERTICAL)
            }
        }
        binding.content.adapter = adapter
        binding.content.layoutManager = layoutManager
        binding.content.addItemDecoration(AlbumsDivider())
        binding.content.setHasFixedSize(true)
    }

    private fun initToolbar() = with(binding.toolbar) {
        inflateMenu(R.menu.albums_menu)

        val searchItem = binding.toolbar.menu?.findItem(R.id.search)
        val search = searchItem?.actionView as? SearchView
        search?.setOnQueryTextListener(this@AlbumLibraryFragment)

        setNavigationIcon(ru.stersh.musicmagician.feature.library.core.R.drawable.ic_menu_24px)
        setNavigationOnClickListener {
            (requireActivity() as? DrawerNavigation)?.openDrawer()
        }

        setOnMenuItemClickListener { item ->
            return@setOnMenuItemClickListener when (item.itemId) {
                R.id.az_title_sort -> {
                    viewModel.sort(UiAlbumSortOrder.AZ_TITLE)
                    true
                }
                R.id.za_title_sort -> {
                    viewModel.sort(UiAlbumSortOrder.ZA_TITLE)
                    true
                }
                R.id.az_artist_sort -> {
                    viewModel.sort(UiAlbumSortOrder.AZ_ARTIST)
                    true
                }
                R.id.za_artist_sort -> {
                    viewModel.sort(UiAlbumSortOrder.ZA_ARTIST)
                    true
                }
                R.id.by_year_earliest_sort -> {
                    viewModel.sort(UiAlbumSortOrder.EARLIEST_YEAR)
                    true
                }
                R.id.by_year_latest_sort -> {
                    viewModel.sort(UiAlbumSortOrder.LATEST_YEAR)
                    true
                }
                android.R.id.home -> {
                    (requireActivity() as? DrawerNavigation)?.openDrawer()
                    true
                }
                else -> false
            }
        }

        setTitle(ru.stersh.musicmagician.ui.R.string.albums)
    }

    private fun subscribeSortOrder() = lifecycleScope.launchWhenStarted {
        viewModel.sortOrder.collect {
            val itemId = when (it) {
                UiAlbumSortOrder.AZ_TITLE -> R.id.az_title_sort
                UiAlbumSortOrder.ZA_TITLE -> R.id.za_title_sort
                UiAlbumSortOrder.AZ_ARTIST -> R.id.az_artist_sort
                UiAlbumSortOrder.ZA_ARTIST -> R.id.za_artist_sort
                UiAlbumSortOrder.EARLIEST_YEAR -> R.id.by_year_earliest_sort
                UiAlbumSortOrder.LATEST_YEAR -> R.id.by_year_latest_sort
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