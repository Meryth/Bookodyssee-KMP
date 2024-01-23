package com.tailoredapps.bookodyssee_km.android.ui.home

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val homeModule = module {
    viewModelOf(::HomeViewModel)
}