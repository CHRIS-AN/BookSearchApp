package com.chrisan.booksearchapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.chrisan.booksearchapp.R
import com.chrisan.booksearchapp.data.repository.BookSearchRepositoryImpl
import com.chrisan.booksearchapp.databinding.ActivityMainBinding
import com.chrisan.booksearchapp.ui.viewmodel.BookSearchViewModel
import com.chrisan.booksearchapp.ui.viewmodel.BookSearchViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var bookSearchViewModel: BookSearchViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupJetpackNavigation()
        
        // App 이 처음 실행할 때만, search Fragment 화면 고정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
        }

        val bookSearchRepository = BookSearchRepositoryImpl()
        val factory = BookSearchViewModelProviderFactory(bookSearchRepository, this)
        bookSearchViewModel = ViewModelProvider(this, factory)[BookSearchViewModel::class.java]
    }

    private fun setupJetpackNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.booksearch_nav_host_fragment) as NavHostFragment ?: return
        navController = host.navController
        // navigation view 와 controller 를 연결
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}