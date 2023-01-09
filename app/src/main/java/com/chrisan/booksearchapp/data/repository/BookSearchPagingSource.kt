package com.chrisan.booksearchapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.chrisan.booksearchapp.data.api.RetrofitInstance.api
import com.chrisan.booksearchapp.data.model.Book
import com.chrisan.booksearchapp.util.Constants.PAGING_SIZE
import retrofit2.HttpException
import java.io.IOException

class BookSearchPagingSource(
    private val query: String,
    private val sort: String,
) : PagingSource<Int, Book>() {

    // Key 를 만드는 부분
    // 여러가지 이유로 paging 갱신을 할 때 사용한다.
    // 가장 최근에 접근한 page 를 anchorPosition 으로 받고, 그 주위의 page 를 읽어오도록 하는 key 를 가져온다.
    override fun getRefreshKey(state: PagingState<Int, Book>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    // Paging source 를 만드는 부분
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Book> {
        return try {
            val pageNumber = params.key ?: START_PAGE_INDEX
            val response = api.searchBooks(query, sort, pageNumber, params.loadSize)
            val endOfPaginationReached = response.body()?.meta?.isEnd!!

            val data = response.body()?.documents!!
            val prevKey = if (pageNumber == START_PAGE_INDEX) null else pageNumber - 1
            val nextKey = if (endOfPaginationReached) {
                null
            } else {
                pageNumber + (params.loadSize / PAGING_SIZE)
            }
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        // 초기값은 null 이기 때문에, 시작값은 1로 지정
        const val START_PAGE_INDEX = 1
    }
}