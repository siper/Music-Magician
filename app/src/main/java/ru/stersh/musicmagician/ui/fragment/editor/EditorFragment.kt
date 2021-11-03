package ru.stersh.musicmagician.ui.fragment.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.github.terrakok.cicerone.Router
import com.google.android.material.appbar.AppBarLayout
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import moxy.MvpAppCompatFragment
import org.koin.android.ext.android.inject
import ru.stersh.musicmagician.R
import ru.stersh.musicmagician.databinding.FragmentBaseEditorBinding
import ru.stersh.musicmagician.extention.dp
import ru.stersh.musicmagician.extention.gone
import ru.stersh.musicmagician.extention.show
import ru.stersh.musicmagician.presentation.view.editor.EditorView
import ru.stersh.musicmagician.ui.adapter.PagerAdapter
import ru.stersh.musicmagician.utils.android.EmptyOnPageChangeListener
import ru.stersh.musicmagician.utils.picasso.RoundedCornersTransformation
import java.io.File
import kotlin.math.abs

abstract class EditorFragment : MvpAppCompatFragment(), EditorView {
    protected val router by inject<Router>()
    protected val adapter by lazy { PagerAdapter(listOf(getSearch(), getEditor()), childFragmentManager) }

    private var isFabAnimated = false
    private var updateCounter = 0
    private var isSaving = false
    private val pagerCallback = object : EmptyOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            when (position) {
                1 -> {
                    binding.search.isChecked = false
                    binding.editor.isChecked = true
                }
                else -> {
                    binding.search.isChecked = true
                    binding.editor.isChecked = false
                }
            }
        }
    }
    private var _binding: FragmentBaseEditorBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentBaseEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun bindHeader(title: String, subtitle: String, albumart: String) {
        val picasso = if (albumart.startsWith("http")) {
            Picasso
                .get()
                .load(albumart)
        } else {
            Picasso
                .get()
                .load(File(albumart))
        }
        picasso
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .transform(RoundedCornersTransformation(8.dp, 0))
            .error(R.drawable.no_albumart)
            .noFade()
            .fit()
            .into(binding.albumart)
        binding.title.text = title
        binding.subtitle.text = subtitle
        updateCounter++
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        initToolbar()
        initViewPager()
        initAppbar()
        initFab()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> router.exit()
        }
        return super.onOptionsItemSelected(item)
    }

    abstract fun saveTags()

    abstract fun getEditor(): Fragment

    abstract fun getSearch(): Fragment

    private fun initButtons() {
        binding.search.isChecked = true
        binding.editor.addOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.search.isChecked = false
                binding.pager.setCurrentItem(1, true)
            }
            if (!binding.editor.isChecked && !binding.search.isChecked) {
                binding.editor.isChecked = true
            }
        }
        binding.search.addOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.editor.isChecked = false
                binding.pager.setCurrentItem(0, true)
            }
            if (!binding.editor.isChecked && !binding.search.isChecked) {
                binding.search.isChecked = true
            }
        }
    }

    private fun initToolbar() {
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = ""
        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24px)
    }

    private fun initViewPager() {
        binding.pager.adapter = adapter
        binding.pager.addOnPageChangeListener(pagerCallback)
    }

    private fun initAppbar() {
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                if (binding.fab.visibility == View.VISIBLE && !isFabAnimated && updateCounter > 1 && !isSaving) {
                    animHideFab()
                }
            } else {
                if (binding.fab.visibility == View.GONE && updateCounter > 1 && !isSaving) {
                    animShowFab()
                }
            }
        })
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            isSaving = true
            animHideFab()
            saveTags()
        }
    }

    private fun animHideFab() {
        ViewCompat
            .animate(binding.fab)
            .scaleX(0.0f)
            .scaleY(0.0f)
            .alpha(0.0f)
            .setInterpolator(FastOutSlowInInterpolator())
            .withLayer()
            .setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View) {
                    isFabAnimated = true
                }

                override fun onAnimationCancel(view: View) {
                    isFabAnimated = false
                }

                override fun onAnimationEnd(view: View) {
                    isFabAnimated = false
                    view.gone()
                }
            }).start()
    }

    private fun animShowFab() {
        binding.fab.show()
        ViewCompat
            .animate(binding.fab)
            .scaleX(1.0f)
            .scaleY(1.0f)
            .alpha(1.0f)
            .setInterpolator(FastOutSlowInInterpolator())
            .withLayer()
            .setListener(null)
            .start()
    }
}