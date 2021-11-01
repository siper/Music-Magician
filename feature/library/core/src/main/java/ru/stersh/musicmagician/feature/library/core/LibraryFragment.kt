package ru.stersh.musicmagician.feature.library.core

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import moxy.MvpAppCompatFragment
import ru.stersh.musicmagician.databinding.FragmentLibraryBinding
import ru.stersh.musicmagician.data.core.entity.MediastoreItem
import ru.stersh.musicmagician.extention.gone
import ru.stersh.musicmagician.extention.show
import ru.stersh.musicmagician.feature.library.core.databinding.FragmentLibraryBinding
import ru.stersh.musicmagician.ui.activity.MainActivity

abstract class LibraryFragment<T> : MvpAppCompatFragment(), SearchView.OnQueryTextListener, LibraryView<T> {
    protected var menu: Menu? = null
    open val title: String by lazy { getString(R.string.app_name) }
    open val menuLayout: Int = -1

    abstract val adapter: AsyncListDifferDelegationAdapter<ru.stersh.musicmagician.data.core.entity.MediastoreItem>

    abstract fun search(query: String)

    private var _binding: FragmentLibraryBinding? = null
    protected val binding
        get() = _binding!!

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        (activity as MainActivity).unlockDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (menuLayout != -1) {
            inflater.inflate(menuLayout, menu)
            this.menu = menu
            val searchItem = menu.findItem(R.id.search)
            val search = searchItem.actionView as SearchView
            search.setOnQueryTextListener(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            (requireActivity() as MainActivity).openDrawer()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null) {
            search("")
        } else {
            search(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null) {
            search("")
        } else {
            search(newText)
        }
        return true
    }

    override fun showContent(items: List<ru.stersh.musicmagician.data.core.entity.MediastoreItem>) {
        adapter.items = items
        binding.content.show()
        binding.stub.root.gone()
    }

    override fun showStub() {
        binding.stub.root.show()
        binding.stub.stubTitle.text = getString(R.string.media_error_title)
        binding.stub.stubMessage.text = getString(R.string.media_error_message)
        binding.content.gone()
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_24px)
            supportActionBar?.title = title
        }
    }
}