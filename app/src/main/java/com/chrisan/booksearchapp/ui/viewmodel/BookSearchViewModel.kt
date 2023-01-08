package com.chrisan.booksearchapp.ui.viewmodel

import androidx.lifecycle.*
import com.chrisan.booksearchapp.data.model.Book
import com.chrisan.booksearchapp.data.model.SearchResponse
import com.chrisan.booksearchapp.data.repository.BookSearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookSearchViewModel(
    private val bookSearchRepository: BookSearchRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // api
    private val _searchResult = MutableLiveData<SearchResponse>()
    val searchResult: LiveData<SearchResponse> get() = _searchResult

    fun searchBooks(query: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = bookSearchRepository.searchBooks(query, "accuracy", 1, 15)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                _searchResult.postValue(body)
            }
        }
    }

    // Room
    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBooks(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.deleteBooks(book)
    }
    
    val favoriteBooks: LiveData<List<Book>> = bookSearchRepository.getFavoriteBooks()

    // SavedState (쿼리 보존에 사용할 변수)
    var query = String()
        set(value) { // backing field 를 사용하여, 쿼리가 변화되면 해당 값을 바로 반영하도록 함.
            field = value
            savedStateHandle[SAVE_STATE_KEY] = value
        }

    init {
        // 초기화 할 때, 일단 savedState 에서 값을 가져오기.
        query = savedStateHandle.get<String>(SAVE_STATE_KEY) ?: ""
    }


    companion object {
        const val SAVE_STATE_KEY = "query" // savedState KEY
    }
}