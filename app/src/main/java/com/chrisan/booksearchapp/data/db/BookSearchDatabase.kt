package com.chrisan.booksearchapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chrisan.booksearchapp.data.model.Book

@Database(
    entities = [Book::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(OrmConverter::class)
abstract class BookSearchDatabase : RoomDatabase() {

    abstract fun bookSearchDao(): BookSearchDao

//    companion object {
//        @Volatile
//        private var INSTANCE: BookSearchDatabase? = null
//
//        private fun buildDatabase(context: Context): BookSearchDatabase =
//            Room.databaseBuilder(
//                context.applicationContext,
//                BookSearchDatabase::class.java,
//                "favorite-books"
//            ).build()
//
//        fun getInstance(context: Context): BookSearchDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//    }
}

/**
 * Room 은 ORM 의 Primitive Type 과 Boxed Type(Primitive Type 의 Wrapper class)  만을 사용하게끔 제한을 하고 있다.
 * 룸에서 일반 객체로 ORM 을 수행하면, URL Thread 에서 Lazing Loading 처리를 해야한다. 그러면 처리 속도가 느려지게 된다.
 * 그렇다고 그냥 하게되더라도, 필요없는 데이터 모두 로딩 하기 때문에 메모리 낭비가 심해진다.
 */