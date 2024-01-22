package com.tailoredapps.bookodyssee_km.android.registration

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val registrationModule = module {
    viewModelOf(::RegistrationViewModel)
}