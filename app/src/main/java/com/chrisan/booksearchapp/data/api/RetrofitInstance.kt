package com.chrisan.booksearchapp.data.api


/*
    여러 개의 레트로핏 객체가 만들어지면, 자원도 낭비되며 통신에 혼선이 올수 있기 때문에 object 키워드를 사용 했고,
    실제 사용하는 순간에 사용하게 끔 lazy 키워드를 사용했다.
*/
//object RetrofitInstance {
//    private val okHttpClient: OkHttpClient by lazy {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//            .setLevel(HttpLoggingInterceptor.Level.BODY)
//        OkHttpClient.Builder()
//            .addInterceptor(httpLoggingInterceptor)
//            .build()
//    }
//
//    private val retrofit: Retrofit by lazy {
//        Retrofit.Builder()
//            .addConverterFactory(MoshiConverterFactory.create())
//            .client(okHttpClient)
//            .baseUrl(BASE_URL)
//            .build()
//    }
//
//    val api: BookSearchApi by lazy {
//        retrofit.create(BookSearchApi::class.java)
//    }
//}