package com.tailoredapps.bookodyssee_km.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tailoredapps.bookodyssee_km.android.base_ui.BookOdysseeTheme
import com.tailoredapps.bookodyssee_km.android.login.LoginScreen
import com.tailoredapps.bookodyssee_km.android.search.SearchScreen
import com.tailoredapps.bookodyssee_km.android.start.StartScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainActivity)
            modules(appModules)
        }

        setContent {
            PreComposeApp {
                val navigator = rememberNavigator()
                BookOdysseeTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        //TODO: interesting for future, moving the NavHost out of MainActivity to make code easier readable
                        NavHost(
                            navigator = navigator,
                            // Navigation transition for the scenes in this NavHost, this is optional
                            navTransition = NavTransition(),
                            initialRoute = "/search",
                        ) {
                            // Define a scene to the navigation graph
                            scene(
                                // Scene's route path
                                route = "/start"
                            ) {
                                StartScreen(
                                    onLoginClick = {
                                        navigator.navigate("/login")
                                    }
                                )
                            }
                            scene(
                                route = "/login"
                            ) {
                                LoginScreen(onLoginSuccess = { })
                            }
                            scene(
                                route = "/search"
                            ) {
                                SearchScreen()
                            }
                        }
                    }
                }
            }
        }
    }
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
