package com.tailoredapps.bookodyssee_km.android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.ROUTE_MAIN
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.ROUTE_SEARCH
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.bookScreen
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.loginScreen
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.mainScreen
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.navigateToBook
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.navigateToLogin
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.navigateToRegistration
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.registrationScreen
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.searchScreen
import com.tailoredapps.bookodyssee_km.android.navigation.destinations.startScreen

enum class NavHosts(val route: String) {
    App("nav_host_app"),
    Main("nav_host_main")
}

enum class NavGraphs(
    val route: String
) {
    Main("nav_main"),
    Start("nav_start"),
}

/**
 * App's Nav Host
 */
@Composable
fun NavHostController.AppNavHost() {
    NavHost(
        navController = this,
        route = NavHosts.App.route,
        startDestination = NavGraphs.Main.route
    ) {
        navigation(startDestination = ROUTE_MAIN, route = NavGraphs.Main.route) {
            mainScreen { navHostController ->
                navHostController.MainNavHost()
            }
        }
    }
}

/**
 * Main Nav Host
 */
@Composable
fun NavHostController.MainNavHost() {
    NavHost(
        navController = this,
        route = NavHosts.Main.route,
        startDestination = NavGraphs.Start.route
    ) {
        navigation(startDestination = ROUTE_SEARCH, route = NavGraphs.Start.route) {
            startScreen(
                onLoginClick = this@MainNavHost::navigateToLogin,
                onRegisterClick = this@MainNavHost::navigateToRegistration
            )
            loginScreen(onLoginSuccess = { /*this@MainNavHost::navigateToHome*/ })
            registrationScreen(onRegistrationSuccess = {/*this@MainNavHost::navigateToHome*/ })
            searchScreen(onBookClick = this@MainNavHost::navigateToBook)
            bookScreen()
        }
    }
}
