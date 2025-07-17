package com.zanoapps.run.data.di

import com.zanoapps.core.domain.run.SyncRunScheduler
import com.zanoapps.run.data.CreateRunWorker
import com.zanoapps.run.data.DeleteRunWorker
import com.zanoapps.run.data.FetchRunsWorker
import com.zanoapps.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}