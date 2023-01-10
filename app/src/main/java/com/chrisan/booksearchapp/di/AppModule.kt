package com.chrisan.booksearchapp.di

import android.content.Context
import androidx.room.Room
import com.chrisan.booksearchapp.data.api.BookSearchApi
import com.chrisan.booksearchapp.data.db.BookSearchDatabase
import com.chrisan.booksearchapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Retrofit 
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BookSearchApi {
        return retrofit.create(BookSearchApi::class.java)
    }

    // Room
    @Singleton
    @Provides
    fun provideBookSearchDatabase(@ApplicationContext context: Context): BookSearchDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            BookSearchDatabase::class.java,
            "favorite-books"
        ).build()
}