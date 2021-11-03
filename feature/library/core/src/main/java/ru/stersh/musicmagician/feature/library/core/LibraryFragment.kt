package ru.stersh.musicmagician.feature.library.core

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.flow.collect
import ru.stersh.musicmagician.feature.library.core.databinding.FragmentLibraryBinding
import ru.stersh.musicmagician.ui.extension.gone
import ru.stersh.musicmagician.ui.extension.show

abstract class LibraryFragment<T, S, V : LibraryViewModel<T, S>> : Fragment(), SearchView.OnQueryTextListener {
    abstract val viewModel: V
    abstract val titleRes: Int
    abstract val menuLayout: Int
    abstract val searchItemId: Int

    abstract val adapter: AsyncListDifferDelegationAdapter<T>

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

        lifecycleScope.launchWhenStarted {
            viewModel.items.collect {
                if (it.isEmpty()) {
                    showStub()
                } else {
                    showContent(it)
                }
            }
        }
        //(activity as MainActivity).unlockDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(menuLayout, menu)
        val searchItem = menu.findItem(searchItemId)
        val search = searchItem.actionView as SearchView
        search.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            //(requireActivity() as MainActivity).openDrawer()
            return true
        }
        return super.onOptionsItemSelected(item)
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

    private fun showContent(items: List<T>) {
        adapter.items = items
        binding.content.show()
        binding.stub.root.gone()
    }

    private fun showStub() {
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
            supportActionBar?.title = getString(titleRes)
        }
    }
}