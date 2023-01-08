package com.chrisan.booksearchapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chrisan.booksearchapp.databinding.FragmentFavoriteBinding
import com.chrisan.booksearchapp.ui.adapter.BookSearchAdapter
import com.chrisan.booksearchapp.ui.viewmodel.BookSearchViewModel
import com.chrisan.booksearchapp.util.collectLatestStateFlow
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var bookSearchAdapter: BookSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        setupRecyclerView()
        setupTouchHelper(view)
        initObserve()
    }

    private fun initObserve() {
        collectLatestStateFlow(bookSearchViewModel.favoriteBooks) {
            bookSearchAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        bookSearchAdapter = BookSearchAdapter()
        binding.rvFavoriteBooks.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = bookSearchAdapter
        }

        bookSearchAdapter.setOnItemClickListener {
            val action = FavoriteFragmentDirections.actionFragmentFavoriteToFragmentBook(it)
            findNavController().navigate(action)
        }
    }

    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val book = bookSearchAdapter.currentList[position]
                bookSearchViewModel.deleteBook(book)

                Snackbar.make(view, "해당 책 정보를 삭제하였습니다.", Snackbar.LENGTH_SHORT).apply {
                    setAction("되돌리기") {
                        bookSearchViewModel.saveBook(book)
                    }
                }.show()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteBooks) // 스와이프 동작을 recycler view 에 연결
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}