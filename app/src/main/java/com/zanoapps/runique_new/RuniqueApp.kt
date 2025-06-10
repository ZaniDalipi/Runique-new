package com.zanoapps.runique_new

import android.app.Application
import com.zanoapps.auth.data.di.authDataModule
import com.zanoapps.auth.presentation.di.authViewModelModule
import com.zanoapps.core.data.coreDataModule
import com.zanoapps.run.presentation.runViewModelModule
import com.zanoapps.runique_new.di.appModule
import com.zanoapps.runiquenew.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runViewModelModule
            )
        }
    }
}