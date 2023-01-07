package com.chrisan.booksearchapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chrisan.booksearchapp.databinding.FragmentSearchBinding
import com.chrisan.booksearchapp.ui.viewmodel.BookSearchViewModel

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // mainActivity 에서 초기화한 viewModel 을 가져온다.
        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}