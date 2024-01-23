package com.tailoredapps.bookodyssee_km.android.navigation.destinations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tailoredapps.bookodyssee_km.android.navigation.getStringArgOrNull
import com.tailoredapps.bookodyssee_km.android.ui.book.BookScreen

internal const val ROUTE_BOOK_WITH_ARGS: String = "book/{bookId}"

internal fun NavGraphBuilder.bookScreen() {
    composable(ROUTE_BOOK_WITH_ARGS) { navBackStackEntry ->
        BookScreen(bookId = navBackStackEntry.getStringArgOrNull("bookId").orEmpty())
    }
}

internal fun NavController.navigateToBook(bookId: String) =
    this.navigate(ROUTE_BOOK_WITH_ARGS.replace("{bookId}", bookId))

