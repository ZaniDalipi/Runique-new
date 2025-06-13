package com.zanoapps.run.location.di

import com.zanoapps.run.domain.LocationObserver
import com.zanoapps.run.location.AndroidLocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()

}