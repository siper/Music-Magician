package ru.stersh.musicmagician.ui.fragment.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import moxy.MvpAppCompatFragment
import ru.stersh.musicmagician.databinding.FragmentTagSearchBinding
import ru.stersh.musicmagician.data.server.core.entity.Tag
import ru.stersh.musicmagician.data.server.core.entity.TagEntity
import ru.stersh.musicmagician.entity.tag.TagProgressItem
import ru.stersh.musicmagician.extention.gone
import ru.stersh.musicmagician.extention.show
import ru.stersh.musicmagician.presentation.view.editor.TagSearchView
import ru.stersh.musicmagician.ui.adapter.search.TagSearchAdapter

abstract class TagSearchFragment : MvpAppCompatFragment(), TagSearchView {
    private val adapter = TagSearchAdapter { applyTag(it) }
    private val progressItems = listOf(TagProgressItem, TagProgressItem, TagProgressItem, TagProgressItem)

    abstract fun applyTag(tag: ru.stersh.musicmagician.data.server.core.entity.Tag)

    private var _binding: FragmentTagSearchBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.content.adapter = null
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun showProgress() {
        binding.content.show()
        binding.stub.root.gone()
        adapter.items = progressItems
    }

    override fun showContent(tags: List<ru.stersh.musicmagician.data.server.core.entity.TagEntity>) {
        binding.stub.root.gone()
        binding.content.show()
        adapter.items = tags
    }

    override fun showStub() {
        binding.stub.root.show()
        binding.content.gone()
    }

    private fun initAdapter() {
        binding.content.layoutManager = LinearLayoutManager(requireContext())
        binding.content.adapter = adapter
    }
}