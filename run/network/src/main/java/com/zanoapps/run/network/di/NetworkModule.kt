package com.zanoapps.run.network.di

import com.zanoapps.core.domain.run.RemoteRunDataSource
import com.zanoapps.run.network.KtorRemoteRunDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteRunDataSource).bind<RemoteRunDataSource>()
}