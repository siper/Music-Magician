package ru.stersh.musicmagician.feature.privacypolicy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import ru.stersh.musicmagician.feature.privacypolicy.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : Fragment() {
    private val router by inject<Router>()

    private var _binding: FragmentPrivacyPolicyBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initToolbar()
        loadPrivacyPolicy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                router.exit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadPrivacyPolicy() {
        binding.content.loadUrl("file:///android_asset/privacy_policy.html")
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = getString(R.string.privacy_policy)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24px)
        }
    }
}