package com.tailoredapps.bookodyssee_km.android.ui.base_ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class NavigationItem(
    val outlinedIcon: ImageVector,
    val filledIcon: ImageVector,
    val label: String
) {
//    data object Maps : NavigationItem(
//        outlinedIcon = Icons.Outlined.LocationOn,
//        filledIcon = Icons.Filled.LocationOn,
//        label = "Libraries"
//    )

    data object Home : NavigationItem(
        outlinedIcon = Icons.Outlined.AutoStories,
        filledIcon = Icons.Filled.AutoStories,
        label = "home"
    )

    data object Finished :
        NavigationItem(
            outlinedIcon = Icons.Outlined.Book,
            filledIcon = Icons.Filled.Book,
            label = "finished"
        )
}

//TODO: uncomment maps when integrating google maps
@Composable
fun AppNavigationBar(
    navController: NavController,
//    onLibrariesClick: () -> Unit,
    onToReadClick: () -> Unit,
    onFinishedClick: () -> Unit,
) {

    val currentScreen = navController.currentBackStackEntryAsState().value
    currentScreen

    val navigationItemList = listOf(
//        NavigationItem.Maps,
        NavigationItem.Home,
        NavigationItem.Finished
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        navigationItemList.forEach { navigationItem ->
            NavigationBarItem(
                icon = {
                    if (currentScreen?.destination?.route == navigationItem.label) {
                        Icon(imageVector = navigationItem.filledIcon, contentDescription = null)
                    } else {
                        Icon(imageVector = navigationItem.outlinedIcon, contentDescription = null)
                    }
                },
                label = {
                    Text(text = navigationItem.label)
                },
                selected = currentScreen?.destination?.route == navigationItem.label,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary
                ),
                enabled = currentScreen?.destination?.route != navigationItem.label,
                onClick = {
                    when (navigationItem) {
                        is NavigationItem.Home -> onToReadClick()
                        is NavigationItem.Finished -> onFinishedClick()
                    }
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppNavigationBarPreview() {
    MaterialTheme {
        AppNavigationBar(
            navController = rememberNavController(),
//            onLibrariesClick = {},
            onToReadClick = {},
            onFinishedClick = {}
        )
    }
}