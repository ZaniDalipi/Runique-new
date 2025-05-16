package com.zanoapps.core.data

import android.content.SharedPreferences
import com.zanoapps.core.data.auth.EncryptedSessionStorage
import com.zanoapps.core.data.networking.HttpClientFactory
import com.zanoapps.core.domain.SessionStorage
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single{
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

}