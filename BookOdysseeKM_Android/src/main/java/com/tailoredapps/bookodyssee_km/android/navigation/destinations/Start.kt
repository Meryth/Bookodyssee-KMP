package com.tailoredapps.bookodyssee_km.android.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee_km.android.start.StartScreen

internal const val ROUTE_START: String = "start"

internal fun NavGraphBuilder.startScreen(onLoginClick: () -> Unit, onRegisterClick: () -> Unit) {
    composable(ROUTE_START) {
        StartScreen(
            onLoginClick = onLoginClick,
            onRegisterClick = onRegisterClick
        )
    }
}

internal fun NavController.navigateToWelcome() =
    this.navigate(ROUTE_START)
