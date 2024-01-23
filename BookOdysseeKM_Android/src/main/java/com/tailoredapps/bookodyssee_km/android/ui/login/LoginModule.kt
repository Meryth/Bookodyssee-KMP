package com.tailoredapps.bookodyssee_km.android.ui.login

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val loginModule = module {
    viewModelOf(::LoginViewModel)
}