package com.zanoapps.run.presentation

import com.zanoapps.run.domain.RunningTracker
import com.zanoapps.run.presentation.active_run.ActiveRunViewModel
import com.zanoapps.run.presentation.run_overview.RunOverviewViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val runPresentationModule = module {
   singleOf(::RunningTracker)

    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)

}