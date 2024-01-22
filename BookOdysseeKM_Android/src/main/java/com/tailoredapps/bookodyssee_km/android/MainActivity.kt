package com.tailoredapps.bookodyssee_km.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.tailoredapps.bookodyssee_km.android.base_ui.BookOdysseeTheme
import com.tailoredapps.bookodyssee_km.android.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val view = LocalView.current
            val darkTheme = isSystemInDarkTheme()

            MainView()

            SideEffect {
                val window = this.window

                window.statusBarColor = Color.Transparent.toArgb()
                window.navigationBarColor = Color.Transparent.toArgb()

                window.isNavigationBarContrastEnforced = false

                val windowsInsetsController = WindowCompat.getInsetsController(window, view)

                windowsInsetsController.isAppearanceLightStatusBars = !darkTheme
                windowsInsetsController.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()
    navController.AppNavHost()
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    BookOdysseeTheme {
        GreetingView("Hello, Android!")
    }
}
