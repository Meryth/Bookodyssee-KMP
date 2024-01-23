package com.tailoredapps.bookodyssee_km.android.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee_km.android.ui.login.LoginScreen

internal const val ROUTE_LOGIN: String = "login"

internal fun NavGraphBuilder.loginScreen(onLoginSuccess: () -> Unit) {
    composable(ROUTE_LOGIN) {
        LoginScreen(onLoginSuccess)
    }
}

internal fun NavController.navigateToLogin() =
    this.navigate(ROUTE_LOGIN)
