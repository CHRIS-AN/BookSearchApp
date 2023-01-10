package com.chrisan.booksearchapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BookSearchApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    // worker class 가 HiltWorkFactory 를 통해 생성되게 된다.
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    // WorkManager 를 초기화 하는 작업 App Startup 에 WorkManager Initializer 를 통해서 앱이 시작될 때 자동으로 이루어진다.
    // 하지만, WorkManager 초기화 방식을 Custom 한 경우, WorkManager Initializer 실행 되지 않도록
    // manifest 에 설정을 추가하여 초기에 시작되지 않게 막아야한다.
}