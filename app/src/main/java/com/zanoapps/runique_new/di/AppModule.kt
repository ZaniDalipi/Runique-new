package com.zanoapps.runique_new.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {  // Creates a Koin DI module
    single<SharedPreferences> {

       val masterKey = MasterKey.Builder(androidApplication())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Provides a singleton instance of SharedPreferences
        EncryptedSharedPreferences(  // Creates encrypted SharedPreferences
            context = androidApplication(),  // Gets the Android Application context
            fileName = "auth_pref",  // Name of the preferences file
            masterKey = masterKey,  // Creates/retrieves a master key for encryption
            prefKeyEncryptionScheme = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            prefValueEncryptionScheme = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}