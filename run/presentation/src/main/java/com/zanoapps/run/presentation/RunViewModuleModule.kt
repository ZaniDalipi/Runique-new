package com.zanoapps.run.presentation

import com.zanoapps.run.presentation.active_run.ActiveRunViewModel
import com.zanoapps.run.presentation.run_overview.RunOverviewViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val runViewModelModule = module {
    viewModelOf(::RunOverviewViewModel)
    viewModelOf(::ActiveRunViewModel)
}