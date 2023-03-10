package com.chrisan.booksearchapp.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.chrisan.booksearchapp.R
import com.chrisan.booksearchapp.databinding.FragmentSettingsBinding
import com.chrisan.booksearchapp.ui.viewmodel.SettingsViewModel
import com.chrisan.booksearchapp.util.Sort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    //private lateinit var bookSearchViewModel: BookSearchViewModel
    //private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        saveSettings()
        loadSettings()
        showWorkStatus()
    }

    // LiveData 를 반환 받은 작업상태를 표시한다.
    private fun showWorkStatus() {
        settingsViewModel.getWorkStatus().observe(viewLifecycleOwner) { workInfo ->
            Log.d("WorkManager", workInfo.toString())
            if (workInfo.isEmpty()) { // 초기에는 값이 존재하지 않는다.
                binding.tvWorkStatus.text = "No works"
            } else {
                binding.tvWorkStatus.text = workInfo[0].state.toString()
            }
        }
    }

    private fun loadSettings() {
        lifecycleScope.launch {
            val buttonId = when (settingsViewModel.getSortMode()) {
                Sort.ACCURACY.value -> R.id.rb_accuracy
                Sort.LATEST.value -> R.id.rb_latest
                else -> return@launch
            }

            binding.rgSort.check(buttonId)
        }

        // WorkManager
        lifecycleScope.launch {
            val mode = settingsViewModel.getCacheDeleteMode()
            binding.swCacheDelete.isChecked = mode
        }
    }

    private fun saveSettings() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) {
                R.id.rb_accuracy -> Sort.ACCURACY.value
                R.id.rb_latest -> Sort.LATEST.value
                else -> return@setOnCheckedChangeListener
            }

            settingsViewModel.saveSortMode(value)
        }

        binding.swCacheDelete.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveCacheDeleteMode(isChecked)
            if (isChecked) {
                settingsViewModel.setWork()
            } else {
                settingsViewModel.deleteWork()
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
