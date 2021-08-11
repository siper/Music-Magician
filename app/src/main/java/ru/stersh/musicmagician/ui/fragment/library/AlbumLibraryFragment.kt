package ru.stersh.musicmagician.ui.fragment.library

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.Router
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import org.koin.core.get
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.Screens
import ru.stersh.musicmagician.di.Di
import ru.stersh.musicmagician.entity.app.AlbumProgressItem
import ru.stersh.musicmagician.entity.app.ui.AlbumSortOrder
import ru.stersh.musicmagician.extention.gone
import ru.stersh.musicmagician.extention.show
import ru.stersh.musicmagician.presentation.presenter.library.AlbumLibraryPresenter
import ru.stersh.musicmagician.ui.adapter.library.AlbumLibraryAdapter
import ru.stersh.musicmagician.ui.divider.AlbumsDivider

class AlbumLibraryFragment : LibraryFragment() {
    private val router by inject<Router>()
    private val presenter by moxyPresenter {
        AlbumLibraryPresenter(Di.get(), Di.get())
    }
    private val progressItems: List<AlbumProgressItem> by lazy {
        val items = mutableListOf<AlbumProgressItem>()
        for (i in 1..30) {
            items.add(AlbumProgressItem)
        }
        return@lazy items.toList()
    }
    override val adapter = AlbumLibraryAdapter { router.navigateTo(Screens.albumEditor(it)) }
    override val title: String by lazy { getString(R.string.drawer_albums) }
    override val menuLayout: Int = R.menu.albums_menu

    override fun search(query: String) = presenter.search(query)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.az_title_sort -> {
                presenter.sort(AlbumSortOrder.AZ_TITLE)
                return true
            }
            R.id.za_title_sort -> {
                presenter.sort(AlbumSortOrder.ZA_TITLE)
                return true
            }
            R.id.az_artist_sort -> {
                presenter.sort(AlbumSortOrder.AZ_ARTIST)
                return true
            }
            R.id.za_artist_sort -> {
                presenter.sort(AlbumSortOrder.ZA_ARTIST)
                return true
            }
            R.id.by_year_earliest_sort -> {
                presenter.sort(AlbumSortOrder.EARLEST_YEAR)
                return true
            }
            R.id.by_year_latest_sort -> {
                presenter.sort(AlbumSortOrder.LATEST_YEAR)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setSortOrder(order: Int) {
        val itemId = when (order) {
            AlbumSortOrder.ZA_TITLE.order -> R.id.za_title_sort
            AlbumSortOrder.AZ_ARTIST.order -> R.id.az_artist_sort
            AlbumSortOrder.ZA_ARTIST.order -> R.id.za_artist_sort
            AlbumSortOrder.EARLEST_YEAR.order -> R.id.by_year_earliest_sort
            AlbumSortOrder.LATEST_YEAR.order -> R.id.by_year_latest_sort
            else -> R.id.az_title_sort
        }
        menu?.findItem(itemId)?.isChecked = true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        setSortOrder(presenter.getSortOrder())
        super.onPrepareOptionsMenu(menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun showProgress() {
        adapter.items = progressItems
        binding.content.show()
        binding.stub.root.gone()
    }
}