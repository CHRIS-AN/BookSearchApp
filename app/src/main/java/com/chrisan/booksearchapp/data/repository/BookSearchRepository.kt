package com.chrisan.booksearchapp.data.repository

import androidx.lifecycle.LiveData
import com.chrisan.booksearchapp.data.model.Book
import com.chrisan.booksearchapp.data.model.SearchResponse
import retrofit2.Response

interface BookSearchRepository {
    suspend fun searchBooks(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Response<SearchResponse>

    suspend fun insertBooks(book: Book)

    suspend fun deleteBooks(book: Book)

    fun getFavoriteBooks(): LiveData<List<Book>>
}