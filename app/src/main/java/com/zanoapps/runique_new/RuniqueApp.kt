package com.zanoapps.runique_new

import android.app.Application
import com.zanoapps.auth.data.di.authDataModule
import com.zanoapps.auth.presentation.di.authViewModelModule
import com.zanoapps.core.data.coreDataModule
import com.zanoapps.core.database.di.databaseModule
import com.zanoapps.run.data.di.runDataModule
import com.zanoapps.run.location.di.locationModule
import com.zanoapps.run.network.di.networkModule
import com.zanoapps.run.presentation.runPresentationModule
import com.zanoapps.runique_new.di.appModule
import com.zanoapps.runiquenew.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import timber.log.Timber

class RuniqueApp: Application() {

    val applicationScope  = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RuniqueApp)
            workManagerFactory()
            modules(
                authDataModule,
                authViewModelModule,
                appModule,
                coreDataModule,
                runPresentationModule,
                locationModule,
                databaseModule,
                networkModule,
                runDataModule
            )
        }
    }
}