package com.zanoapps.auth.data.di

import com.zanoapps.auth.data.AuthRepositoryImpl
import com.zanoapps.auth.data.EmailPatternValidator
import com.zanoappsa.auth.domain.AuthRepository
import com.zanoappsa.auth.domain.PatternValidator
import com.zanoappsa.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }

    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()


}