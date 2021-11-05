package ru.stersh.musicmagician.feature.editor.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.stersh.musicmagician.feature.editor.core.databinding.FragmentTagSearchBinding
import ru.stersh.musicmagician.ui.extension.gone
import ru.stersh.musicmagician.ui.extension.show

abstract class TagSearchFragment : Fragment() {
    private val adapter = TagSearchAdapter { applyTag(it) }

    abstract fun applyTag(tag: UiItem)

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

    protected fun showContent(tags: List<UiItem>) {
        if (tags.isEmpty()) {
            binding.stub.root.show()
            binding.content.gone()
        } else {
            binding.stub.root.gone()
            binding.content.show()
            adapter.items = tags
        }
    }

    private fun initAdapter() {
        binding.content.layoutManager = LinearLayoutManager(requireContext())
        binding.content.adapter = adapter
    }
}