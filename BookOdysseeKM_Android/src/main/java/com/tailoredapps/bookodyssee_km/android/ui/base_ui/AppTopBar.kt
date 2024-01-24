package com.tailoredapps.bookodyssee_km.android.ui.base_ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AppTopBarSize {
    Small, Medium, Large
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    usesTonalElevation: Boolean = true,
    size: AppTopBarSize = AppTopBarSize.Small,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    color: Color = MaterialTheme.colorScheme.primary
) = when (size) {
    AppTopBarSize.Small -> AppTopBarSmall(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = color,
            scrolledContainerColor = MaterialTheme.colorScheme.applyTonalElevation(
                backgroundColor = color,
                elevation = ElevationTokens.Level2,
                usesTonalElevation = usesTonalElevation
            )
        )
    )

    AppTopBarSize.Medium -> AppTopBarMedium(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = color,
            scrolledContainerColor = MaterialTheme.colorScheme.applyTonalElevation(
                backgroundColor = color,
                elevation = ElevationTokens.Level2,
                usesTonalElevation = usesTonalElevation
            )
        )
    )

    AppTopBarSize.Large -> AppTopBarLarge(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = color,
            scrolledContainerColor = MaterialTheme.colorScheme.applyTonalElevation(
                backgroundColor = color,
                elevation = ElevationTokens.Level2,
                usesTonalElevation = usesTonalElevation
            )
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBarSmall(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) = TopAppBar(
    title = { Text(text = title) },
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions,
    windowInsets = windowInsets,
    colors = colors,
    scrollBehavior = scrollBehavior
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBarMedium(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.mediumTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) = MediumTopAppBar(
    title = { Text(text = title) },
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions,
    windowInsets = windowInsets,
    colors = colors,
    scrollBehavior = scrollBehavior
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppTopBarLarge(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.largeTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) = LargeTopAppBar(
    title = { Text(text = title) },
    modifier = modifier,
    navigationIcon = navigationIcon,
    actions = actions,
    windowInsets = windowInsets,
    colors = colors,
    scrollBehavior = scrollBehavior
)

class AppTopBarPropertiesPreviewProvider :
    PreviewParameterProvider<AppTopBarSize> {
    override val values = sequenceOf(
        AppTopBarSize.Small,
        AppTopBarSize.Medium,
        AppTopBarSize.Large
    )
}

/**
 * Returns the new background [Color] to use, representing the original background [color] with an
 * overlay corresponding to [elevation] applied. The overlay will only be applied to
 * [ColorScheme.surface].
 */
fun ColorScheme.applyTonalElevation(
    backgroundColor: Color,
    elevation: Dp,
    usesTonalElevation: Boolean
): Color {
    return if (backgroundColor == surface && usesTonalElevation) {
        surfaceColorAtElevation(elevation)
    } else {
        backgroundColor
    }
}

@Suppress("unused")
object ElevationTokens {
    val Level0 = 0.0.dp
    val Level1 = 1.0.dp
    val Level2 = 3.0.dp
    val Level3 = 6.0.dp
    val Level4 = 8.0.dp
    val Level5 = 12.0.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun AppTopBarSmall(
    @PreviewParameter(AppTopBarPropertiesPreviewProvider::class) size: AppTopBarSize
) {
    BookOdysseeTheme {
        AppTopBar(
            title = "Test",
            size = size,
            usesTonalElevation = true
        )
    }
}
