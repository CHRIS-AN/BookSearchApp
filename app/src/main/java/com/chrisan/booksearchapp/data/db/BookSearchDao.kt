package com.chrisan.booksearchapp.data.db

import androidx.room.*
import com.chrisan.booksearchapp.data.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookSearchDao {
    // OnConflictStrategy.REPLACE : 만약 동일한 primary key 값이 존재할 경우 덮어씌우기
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    // @Query 애노테이션은 시간이 덜 걸리기 때문에 코루틴 내에 비동기로 동작할 필요가 없다.
    @Query("SELECT * FROM books")
    fun getFavoriteBooks(): Flow<List<Book>>
}