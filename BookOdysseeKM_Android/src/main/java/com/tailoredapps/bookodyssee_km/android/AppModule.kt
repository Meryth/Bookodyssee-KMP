package com.tailoredapps.bookodyssee_km.android

import com.tailoredapps.bookodyssee_km.UserSDK
import com.tailoredapps.bookodyssee_km.android.book.bookModule
import com.tailoredapps.bookodyssee_km.android.login.loginModule
import com.tailoredapps.bookodyssee_km.android.registration.registrationModule
import com.tailoredapps.bookodyssee_km.android.search.searchModule
import com.tailoredapps.bookodyssee_km.cache.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val appModule = module {
    single { UserSDK(DatabaseDriverFactory(androidContext())) }

    //TODO: will probably need for shared prefs if there is no alternative from KMP
}


internal val appModules = listOf(
    appModule,
    loginModule,
    registrationModule,
    searchModule,
    bookModule,
)
